package com.fourbeat.presentation.ui.main.groupdetail

import com.fourbeat.presentation.model.group.GroupFeedUiData

data class GroupDetailUiState(
    val isLoading: Boolean = false,
    val previousFeed: GroupFeedUiData? = null,
    val currentFeed: GroupFeedUiData? = null,
    val nextFeed: GroupFeedUiData? = null,
)

sealed interface GroupDetailEvent {
    data class OnDateChanged(val date: String) : GroupDetailEvent
    data object OnBackClicked : GroupDetailEvent
}

sealed interface GroupDetailSideEffect {
    data object NavigateToBack : GroupDetailSideEffect
}
