package com.fourbeat.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthScreen {
    @Serializable
    data object Login : AuthScreen

    @Serializable
    data class Register(val email: String, val nickname: String?) : AuthScreen
}

sealed interface MainScreen {
    @Serializable
    data object Home : MainScreen

    @Serializable
    data object CreateGroup : MainScreen

    @Serializable
    data object JoinGroupDialog : MainScreen

    @Serializable
    data class GroupDetail(val groupId: Long)

    @Serializable
    data class ShareGroupCodeDialog(val code: String) : MainScreen

    @Serializable
    data class SelectSong(val groupId: Long)

    @Serializable
    data class CreatePost(
        val groupId: Long,
        val songTitle: String,
        val songArtist: String,
        val songImageUrl: String?,
    )

    @Serializable
    data object Camera : MainScreen
}