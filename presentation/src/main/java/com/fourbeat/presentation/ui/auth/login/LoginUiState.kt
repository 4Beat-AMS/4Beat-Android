package com.fourbeat.presentation.ui.auth.login

import android.content.Context

data class LoginUiState(
    val isLoading: Boolean = false,
)

sealed interface LoginEvent {
    data class OnLoginButtonClicked(val context: Context) : LoginEvent
}

sealed interface LoginSideEffect {
    data class NavigateToRegister(val email: String): LoginSideEffect

    data object NavigateToHome : LoginSideEffect
}
