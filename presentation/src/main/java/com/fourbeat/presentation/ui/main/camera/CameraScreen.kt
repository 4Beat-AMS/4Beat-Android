package com.fourbeat.presentation.ui.main.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import timber.log.Timber

@Composable
fun CameraRoute(
    navigateBack: (filePath: String) -> Unit,
    viewModel: CameraViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val uiState = viewModel.uiState
    val videoCapture = remember(uiState.cameraLens) {
        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
            .build()
        VideoCapture.withOutput(recorder)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is CameraSideEffect.SaveVideoAndNavigateBack -> navigateBack(effect.filePath)
            }
        }
    }

    LaunchedEffect(uiState.cameraLens, videoCapture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().apply {
            surfaceProvider = previewView.surfaceProvider
        }
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner = lifecycleOwner,
                cameraSelector = CameraSelector.Builder().requireLensFacing(uiState.cameraLens).build(),
                preview, videoCapture
            )
            viewModel.onEvent(CameraEvent.OnVideoCaptureReady(videoCapture))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    CameraScreen(
        uiState = viewModel.uiState,
        previewView = previewView,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun CameraScreen(
    uiState: CameraUiState,
    previewView: PreviewView,
    onEvent: (CameraEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { previewView }
        )
        if (uiState.isRecording) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "${uiState.remainingSeconds}",
                color = Color.White,
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        RecordButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            isRecording = uiState.isRecording,
            onClick = { onEvent(CameraEvent.OnRecordButtonClicked) },
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 48.dp, end = 24.dp),
            onClick = { onEvent(CameraEvent.OnCameraFlipClicked) },
            enabled = !uiState.isRecording,
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun RecordButton(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier.size(72.dp),
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Red,
                    shape = if (isRecording) RoundedCornerShape(8.dp) else CircleShape,
                ),
        )
    }
}
