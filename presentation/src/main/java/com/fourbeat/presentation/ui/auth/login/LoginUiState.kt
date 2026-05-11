package com.fourbeat.presentation.ui.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,
)

sealed interface LoginEvent {
    data object OnLoginButtonClicked : LoginEvent
}

sealed interface LoginSideEffect {
    data class NavigateToRegister(val email: String): LoginSideEffect

    data object NavigateToHome : LoginSideEffect
}
