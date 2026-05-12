package com.fourbeat.presentation.ui.main.home

import com.fourbeat.presentation.model.group.GroupUiModel

data class HomeUiState(
    val groups: List<GroupUiModel> = emptyList(),
    val isLoading: Boolean = false,
)

sealed interface HomeEvent {
    data object OnPlusIconClicked : HomeEvent
    data object OnHashIconClicked : HomeEvent
    data class OnGroupItemClicked(val groupId: Long) : HomeEvent
}

sealed interface HomeSideEffect {
    data object NavigateToCreateGroup : HomeSideEffect
    data object NavigateToJoinGroupDialog : HomeSideEffect
    data class NavigateToGroupDetail(val groupId: Long) : HomeSideEffect
}
