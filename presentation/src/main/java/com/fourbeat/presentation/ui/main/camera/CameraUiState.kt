package com.fourbeat.presentation.ui.main.camera

data class CameraUiState(
    val isRecording: Boolean = false,
    val remainingSeconds: Int = MAX_RECORDING_SECONDS,
    val isFrontCamera: Boolean = false,
) {
    companion object {
        const val MAX_RECORDING_SECONDS = 4
    }
}

sealed interface CameraEvent {
    data object OnRecordButtonClicked : CameraEvent
    data object OnCameraFlipClicked : CameraEvent
}

sealed interface CameraSideEffect {
    data class SaveVideoAndNavigateBack(val filePath: String) : CameraSideEffect
}
