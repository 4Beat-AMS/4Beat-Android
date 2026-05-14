package com.fourbeat.presentation.ui.main.createpost

import java.io.File

data class CreatePostUiState(
    val announce: String = "",
    val videoFile: File? = null,
    val comment: String = "",
    val isLoading: Boolean = false,
)

sealed interface CreatePostEvent {
    data object OnVideoBoxClicked : CreatePostEvent
    data class OnCameraPermissionResult(val granted: Boolean) : CreatePostEvent
    data class OnVideoFileSelected(val file: File) : CreatePostEvent
    data object OnVideoDeleted : CreatePostEvent
    data class OnCommentChanged(val comment: String) : CreatePostEvent
    data object OnUploadClicked : CreatePostEvent
}

sealed interface CreatePostSideEffect {
    data object CheckCameraPermission : CreatePostSideEffect
    data object NavigateToCamera : CreatePostSideEffect
    data class NavigateToGroupDetail(val groupId: Long) : CreatePostSideEffect
}
