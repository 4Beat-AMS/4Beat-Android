package com.fourbeat.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthScreen {
    @Serializable
    data object Login : AuthScreen

    @Serializable
    data class Register(val email: String) : AuthScreen
}

sealed interface MainScreen {
    @Serializable
    data object Home : MainScreen
}