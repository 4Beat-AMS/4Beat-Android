package com.fourbeat.presentation.ui.main.home

import com.fourbeat.presentation.model.group.GroupUiModel

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
    data class Success(val groups: List<GroupUiModel>) : HomeUiState()
}

sealed interface HomeEvent {
    data object OnPlusIconClicked : HomeEvent
    data object OnHashIconClicked : HomeEvent
    data object OnRefresh : HomeEvent
    data class OnGroupItemClicked(val groupId: Long) : HomeEvent
}

sealed interface HomeSideEffect {
    data object NavigateToCreateGroup : HomeSideEffect
    data object NavigateToJoinGroupDialog : HomeSideEffect
    data class NavigateToGroupDetail(val groupId: Long) : HomeSideEffect
}
