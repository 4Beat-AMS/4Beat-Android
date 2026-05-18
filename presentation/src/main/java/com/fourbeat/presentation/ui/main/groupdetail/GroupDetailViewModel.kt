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

    init {
        loadInitial(DateProvider.today())
    }

    fun onEvent(event: GroupDetailEvent) {
        when (event) {
            GroupDetailEvent.OnScrollToPrev -> scrollToPrev()
            GroupDetailEvent.OnScrollToNext -> scrollToNext()
        }
    }

    private fun loadInitial(date: String) {
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

    private fun scrollToPrev() {
        val prevFeed = uiState.previousFeed ?: return
        val oldCurrent = uiState.currentFeed
        uiState = uiState.copy(
            previousFeed = null,
            currentFeed = prevFeed,
            nextFeed = oldCurrent,
        )
        viewModelScope.launch {
            prevFeed.previousDate?.let { prefetch(it, isPrevious = true) }
        }
    }

    private fun scrollToNext() {
        val nextFeed = uiState.nextFeed ?: return
        val oldCurrent = uiState.currentFeed
        uiState = uiState.copy(
            previousFeed = oldCurrent,
            currentFeed = nextFeed,
            nextFeed = null,
        )
        viewModelScope.launch {
            nextFeed.nextDate?.let { prefetch(it, isPrevious = false) }
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
