package com.fourbeat.presentation.ui.main.joingroup

import com.fourbeat.presentation.model.common.Validatable

data class JoinGroupUiState(
    val code: String = "",
    val isLoading: Boolean = false,
) : Validatable {
    override val isValid: Boolean
        get() = isLoading.not() && code.length == CODE_LENGTH

    companion object {
        const val CODE_LENGTH = 6
    }
}

sealed interface JoinGroupEvent {
    data class OnCodeChanged(val code: String) : JoinGroupEvent
    data object OnJoinButtonClicked : JoinGroupEvent
}

sealed interface JoinGroupSideEffect {
    data class NavigateToGroupDetail(val groupId: Long) : JoinGroupSideEffect
}
