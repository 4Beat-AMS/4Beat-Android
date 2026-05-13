package com.fourbeat.presentation.ui.main.selectsong

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.usecase.media.GetMediaSongFlow
import com.fourbeat.presentation.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SelectSongViewModel @Inject constructor(
    getMediaSongFlow: GetMediaSongFlow,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val groupId = savedStateHandle.toRoute<MainScreen.SelectSong>().groupId

    val liveSongFlow: StateFlow<LiveSongUiState> =
        getMediaSongFlow()
            .map { song ->
                Timber.i(song.toString())
                if (song != null) {
                    LiveSongUiState.Live(song)
                } else {
                    LiveSongUiState.None
                }
            }
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
        }
    }

    private fun toggleSong(song: Song) {
        uiState = uiState.copy(
            selectedSong = if (uiState.selectedSong == song) null else song,
        )
    }
}
