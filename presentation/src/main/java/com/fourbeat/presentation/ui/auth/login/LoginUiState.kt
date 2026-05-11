package com.fourbeat.presentation.ui.auth.login

data class LoginUiState(
    val isLoading: Boolean = false,
)

sealed interface LoginEvent {
    data class OnLoginButtonClicked(val email: String) : LoginEvent
}

sealed interface LoginSideEffect {
    data class NavigateToHome(val uid: Long) : LoginSideEffect
}
