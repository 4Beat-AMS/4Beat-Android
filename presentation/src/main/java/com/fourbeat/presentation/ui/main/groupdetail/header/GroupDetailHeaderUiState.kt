package com.fourbeat.presentation.ui.main.groupdetail.header

import com.fourbeat.presentation.model.group.GroupUiModel

data class GroupDetailHeaderUiState(
    val group: GroupUiModel,
)

sealed interface GroupDetailHeaderEvent {
    data object OnPlusIconClicked : GroupDetailHeaderEvent
    data object OnHashIconClicked : GroupDetailHeaderEvent
}

sealed interface GroupDetailHeaderSideEffect {
    data object NavigateToCreatePost : GroupDetailHeaderSideEffect
    data object ShowGroupCodeDialog : GroupDetailHeaderSideEffect
}
