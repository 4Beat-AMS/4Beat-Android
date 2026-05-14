package com.fourbeat.presentation.ui.main.camera

import android.content.Context
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(CameraUiState())
        private set

    private val _sideEffect = Channel<CameraSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var recording: Recording? = null
    private var countdownJob: Job? = null

    fun onEvent(event: CameraEvent) {
        when (event) {
            CameraEvent.OnRecordButtonClicked -> {
                if (uiState.isRecording) stopRecording()
            }
        }
    }

    fun startRecording(videoCapture: VideoCapture<Recorder>, context: Context) {
        val outputFile = File(context.cacheDir, "video_4beat_${System.currentTimeMillis()}.mp4")
        recording = videoCapture.output
            .prepareRecording(context, FileOutputOptions.Builder(outputFile).build())
            .start(ContextCompat.getMainExecutor(context)) { event ->
                if (event is VideoRecordEvent.Finalize && !event.hasError()) {
                    viewModelScope.launch {
                        _sideEffect.send(
                            CameraSideEffect.SaveVideoAndNavigateBack(outputFile.absolutePath)
                        )
                    }
                }
            }

        uiState = uiState.copy(isRecording = true)
        startCountdown()
    }

    private fun startCountdown() {
        countdownJob = viewModelScope.launch {
            repeat(CameraUiState.MAX_RECORDING_SECONDS) { elapsed ->
                uiState = uiState.copy(remainingSeconds = CameraUiState.MAX_RECORDING_SECONDS - elapsed)
                delay(1_000L)
            }
            stopRecording()
        }
    }

    fun stopRecording() {
        countdownJob?.cancel()
        countdownJob = null
        recording?.stop()
        recording = null
        uiState = uiState.copy(isRecording = false)
    }

    override fun onCleared() {
        super.onCleared()
        recording?.stop()
    }
}
