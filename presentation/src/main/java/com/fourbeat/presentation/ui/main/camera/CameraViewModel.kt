package com.fourbeat.presentation.ui.main.camera

import android.content.Context
import androidx.camera.core.CameraSelector
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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context
) : ViewModel() {
    var uiState by mutableStateOf(CameraUiState())
        private set

    private val _sideEffect = Channel<CameraSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null
    private var countdownJob: Job? = null

    fun onEvent(event: CameraEvent) {
        when (event) {
            is CameraEvent.OnVideoCaptureReady -> {
                this.videoCapture = event.videoCapture
            }
            is CameraEvent.OnRecordButtonClicked -> {
                if (uiState.isRecording) stopRecording() else startRecording()
            }
            CameraEvent.OnCameraFlipClicked -> {
                videoCapture = null
                uiState = uiState.copy(
                    cameraLens = if (uiState.cameraLens == CameraSelector.LENS_FACING_BACK)
                        CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
                )
            }
        }
    }

    private fun resetUiState() {
        uiState = uiState.copy(
            isRecording = false,
            remainingSeconds = CameraUiState.MAX_RECORDING_SECONDS
        )
    }

    /*
    * 영상 촬영 시작
    * 1. 저장될 videoFile 생성
    * 2. 촬영이 시작되면, isRecording=true, 타이머 시작
    * 3. 촬영이 끝나면, 타이머 중지
    * 4. 촬영 에러가 없으면, 생성된 File path 이전 화면으로 보냄
    * 5. 에러가 있으면, 생성된 File 삭제 후 메세지 전송 (TODO)
    * 6. Recording 객체 자원 해체 및 상태 초기화
    * */
    private fun startRecording() {
        val capture = videoCapture ?: return

        val name = "video-4beat-${System.currentTimeMillis()}.mp4"
        val videoFile = File(context.cacheDir, name)
        val outputOptions = FileOutputOptions.Builder(videoFile).build()

        recording = capture.output
            .prepareRecording(context, outputOptions)
            .start(ContextCompat.getMainExecutor(context)) { event ->
                when (event) {
                    is VideoRecordEvent.Start -> {
                        uiState = uiState.copy(isRecording = true)
                        startCountdown()
                    }
                    is VideoRecordEvent.Finalize -> {
                        stopCountDownJob()
                        if (event.hasError().not()) {
                            viewModelScope.launch {
                                val filePath = videoFile.absolutePath
                                Timber.tag("카메라 촬영 path").i(filePath)
                                _sideEffect.send(CameraSideEffect.SaveVideoAndNavigateBack(filePath))
                            }
                        } else {
                            event.cause?.let {
                                Timber.tag("카메라 촬영 에러").e(it)
                            }
                            if (videoFile.exists()) {
                                viewModelScope.launch(Dispatchers.IO) {
                                    videoFile.delete()
                                }
                            }
                        }
                        recording?.close()
                        recording = null
                        resetUiState()
                    }
                }
            }
    }

    /**
     * 타이머 Jop 시작
     * 4초 동안 남은 시간 줄여가며 변경
     * 끝나면 촬영 중지
     * */
    private fun startCountdown() {
        countdownJob = viewModelScope.launch {
            repeat(CameraUiState.MAX_RECORDING_SECONDS) {
                delay(1_000)
                uiState = uiState.copy(remainingSeconds = uiState.remainingSeconds - 1)
            }
            stopRecording()
        }
    }

    private fun stopCountDownJob() {
        countdownJob?.cancel()
        countdownJob = null
    }

    private fun stopRecording() {
        recording?.stop()
    }

    /**
     * ViewModel 메모리에서 해제될 때, 자원 해제
     * */
    override fun onCleared() {
        stopCountDownJob()
        recording?.stop()
        recording = null
        super.onCleared()
    }
}
