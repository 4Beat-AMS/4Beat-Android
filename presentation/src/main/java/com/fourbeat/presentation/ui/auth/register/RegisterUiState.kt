package com.fourbeat.presentation.ui.auth.register

import com.fourbeat.presentation.model.common.Validatable

data class RegisterUiState(
    val name: String = "",
    val nickname: String = "",
    val isLoading: Boolean = false,
) : Validatable {
    override val isValid: Boolean
        get() = isLoading.not() &&
                name.length in 1..NAME_MAX_LENGTH &&
                nickname.length in 1..NICKNAME_MAX_LENGTH

    companion object {
        const val NAME_MAX_LENGTH = 10
        const val NICKNAME_MAX_LENGTH = 10
    }
}

sealed interface RegisterEvent {
    data class OnNameChanged(val name: String) : RegisterEvent
    data class OnNicknameChanged(val nickname: String) : RegisterEvent
    data object OnRegisterButtonClicked : RegisterEvent
    data object OnBackClicked : RegisterEvent
}

sealed interface RegisterSideEffect {
    data object NavigateToHome : RegisterSideEffect
    data object NavigateToBack : RegisterSideEffect
}
