package com.fourbeat.presentation.ui.main.joingroup

import com.fourbeat.presentation.model.common.Validatable

data class JoinGroupUiState(
    val code: String = "",
    val isLoading: Boolean = false,
) : Validatable {
    override val isValid: Boolean
        get() = isLoading.not() && code.isNotBlank()
}

sealed interface JoinGroupEvent {
    data class OnCodeChanged(val code: String) : JoinGroupEvent
    data object OnJoinButtonClicked : JoinGroupEvent
    data object OnOutsideClicked : JoinGroupEvent
}

sealed interface JoinGroupSideEffect {
    data class NavigateToGroupDetail(val groupId: Long) : JoinGroupSideEffect
    data object Dismiss : JoinGroupSideEffect
}
