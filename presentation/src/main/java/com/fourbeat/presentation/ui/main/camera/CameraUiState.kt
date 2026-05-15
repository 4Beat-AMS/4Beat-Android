package com.fourbeat.presentation.ui.main.camera

import androidx.camera.core.CameraSelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture

data class CameraUiState(
    val isRecording: Boolean = false,
    val cameraLens: Int = CameraSelector.LENS_FACING_BACK,
    val remainingSeconds: Int = MAX_RECORDING_SECONDS,
) {
    companion object {
        const val MAX_RECORDING_SECONDS = 4
    }
}

sealed interface CameraEvent {
    data class OnVideoCaptureReady(val videoCapture: VideoCapture<Recorder>) : CameraEvent
    data object OnRecordButtonClicked : CameraEvent
    data object OnCameraFlipClicked : CameraEvent
}

sealed interface CameraSideEffect {
    data class SaveVideoAndNavigateBack(val filePath: String) : CameraSideEffect
}
