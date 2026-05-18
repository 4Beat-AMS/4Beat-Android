package com.fourbeat.presentation.ui.main.groupdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.fourbeat.domain.usecase.group.GetGroupFeedUseCase
import com.fourbeat.presentation.mapper.toUiData
import com.fourbeat.presentation.navigation.MainScreen
import com.fourbeat.presentation.util.DateProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val getGroupFeedUseCase: GetGroupFeedUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val groupId = savedStateHandle.toRoute<MainScreen.GroupDetail>().groupId

    var uiState by mutableStateOf(GroupDetailUiState())
        private set

    private val _sideEffect = Channel<GroupDetailSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        loadFeedWithAdjacent(DateProvider.today())
    }

    fun onEvent(event: GroupDetailEvent) {
        when (event) {
            is GroupDetailEvent.OnDateChanged -> loadFeedWithAdjacent(event.date)
            GroupDetailEvent.OnBackClicked -> viewModelScope.launch {
                _sideEffect.send(GroupDetailSideEffect.NavigateToBack)
            }
        }
    }

    private fun loadFeedWithAdjacent(date: String) {
        viewModelScope.launch {
            uiState = GroupDetailUiState(isLoading = true)
            getGroupFeedUseCase(groupId = groupId, date = date)
                .onSuccess { feed ->
                    uiState = GroupDetailUiState(currentFeed = feed.toUiData())
                    launch { feed.previousDate?.let { prefetch(it, isPrevious = true) } }
                    launch { feed.nextDate?.let { prefetch(it, isPrevious = false) } }
                }
                .onFailure {
                    uiState = uiState.copy(isLoading = false)
                }
        }
    }

    private suspend fun prefetch(date: String, isPrevious: Boolean) {
        getGroupFeedUseCase(groupId = groupId, date = date)
            .onSuccess { feed ->
                uiState = if (isPrevious) {
                    uiState.copy(previousFeed = feed.toUiData())
                } else {
                    uiState.copy(nextFeed = feed.toUiData())
                }
            }
    }
}
