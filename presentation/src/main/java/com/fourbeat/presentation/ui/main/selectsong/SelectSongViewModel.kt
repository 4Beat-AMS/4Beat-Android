package com.fourbeat.presentation.ui.main.selectsong

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.usecase.media.GetLiveSongPermissionFlowUseCase
import com.fourbeat.domain.usecase.media.GetMediaSongFlowUseCase
import com.fourbeat.presentation.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SelectSongViewModel @Inject constructor(
    getMediaSongFlowUseCase: GetMediaSongFlowUseCase,
    getLiveSongPermissionFlowUseCase: GetLiveSongPermissionFlowUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val groupId = savedStateHandle.toRoute<MainScreen.SelectSong>().groupId

    val liveSongFlow: StateFlow<LiveSongUiState> =
        getLiveSongPermissionFlowUseCase()
            .flatMapLatest { granted ->
                if (!granted) {
                    flowOf(LiveSongUiState.PermissionRequired)
                } else {
                    getMediaSongFlowUseCase().map { song ->
                        if (song != null) {
                            LiveSongUiState.Live(song)
                        } else {
                            LiveSongUiState.None
                        }
                    }
                }
            }
            .catch { emit(LiveSongUiState.None) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = LiveSongUiState.Loading
            )

    var uiState by mutableStateOf(SelectSongUiState())
        private set

    private val _sideEffect = Channel<SelectSongSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: SelectSongEvent) {
        when (event) {
            is SelectSongEvent.OnSongItemToggled -> toggleSong(event.song)
            SelectSongEvent.OnNextButtonClicked -> viewModelScope.launch {
                _sideEffect.send(SelectSongSideEffect.NavigateToCreatePost(groupId))
            }
            SelectSongEvent.OnBackIconClicked -> viewModelScope.launch {
                _sideEffect.send(SelectSongSideEffect.NavigateToBack)
            }
            SelectSongEvent.OnRequestPermissionClicked -> viewModelScope.launch {
                _sideEffect.send(SelectSongSideEffect.OpenNotificationListenerSettings)
            }
        }
    }

    private fun toggleSong(song: Song) {
        uiState = uiState.copy(
            selectedSong = if (uiState.selectedSong == song) null else song,
        )
    }
}
