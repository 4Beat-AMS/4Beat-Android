package com.fourbeat.presentation.ui.main.selectsong

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.usecase.media.GetLiveSongPermissionFlowUseCase
import com.fourbeat.domain.usecase.media.GetMediaSongFlowUseCase
import com.fourbeat.domain.usecase.music.SearchSongPageUseCase
import com.fourbeat.presentation.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SelectSongViewModel @Inject constructor(
    private val searchSongPageUseCase: SearchSongPageUseCase,
    getMediaSongFlowUseCase: GetMediaSongFlowUseCase,
    getLiveSongPermissionFlowUseCase: GetLiveSongPermissionFlowUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val groupId = savedStateHandle.toRoute<MainScreen.SelectSong>().groupId

    private val _uiState = MutableStateFlow(SelectSongUiState())
    val uiState: StateFlow<SelectSongUiState> = _uiState.asStateFlow()

    val liveSongFlow: StateFlow<LiveSongUiState> =
        getLiveSongPermissionFlowUseCase()
            .flatMapLatest { granted ->
                if (!granted) {
                    flowOf(LiveSongUiState.PermissionRequired)
                } else {
                    getMediaSongFlowUseCase().map { song ->
                        if (song != null) LiveSongUiState.Live(song)
                        else LiveSongUiState.None
                    }
                }
            }
            .catch { emit(LiveSongUiState.None) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = LiveSongUiState.Loading,
            )

    val songPagingFlow: Flow<PagingData<Song>> =
        _uiState
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .filter { it.isNotBlank() }
            .flatMapLatest { query ->
                Pager(
                    config = PagingConfig(pageSize = 10, initialLoadSize = 10),
                    pagingSourceFactory = { SongSearchPagingSource(query, searchSongPageUseCase) },
                ).flow
            }
            .cachedIn(viewModelScope)

    private val _sideEffect = Channel<SelectSongSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: SelectSongEvent) {
        when (event) {
            is SelectSongEvent.OnSongItemToggled -> _uiState.update {
                it.copy(selectedSong = if (it.selectedSong == event.song) null else event.song)
            }
            is SelectSongEvent.OnSearchQueryChanged -> _uiState.update {
                it.copy(searchQuery = event.query)
            }
            SelectSongEvent.OnNextButtonClicked -> {
                if (!uiState.value.isValid) return
                val song = uiState.value.selectedSong!!
                viewModelScope.launch {
                    _sideEffect.send(SelectSongSideEffect.NavigateToCreatePost(groupId, song))
                }
            }
            SelectSongEvent.OnBackIconClicked -> viewModelScope.launch {
                _sideEffect.send(SelectSongSideEffect.NavigateToBack)
            }
            SelectSongEvent.OnRequestPermissionClicked -> viewModelScope.launch {
                _sideEffect.send(SelectSongSideEffect.OpenNotificationListenerSettings)
            }
        }
    }
}
