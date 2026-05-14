package com.fourbeat.presentation.ui.main.camera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun CameraRoute(
    navigateBack: (filePath: String) -> Unit,
    viewModel: CameraViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        val activity = context as Activity
        val original = activity.requestedOrientation
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose { activity.requestedOrientation = original }
    }

    var surfaceRequest by remember { mutableStateOf<SurfaceRequest?>(null) }
    val recorder = remember {
        Recorder.Builder()
            .setQualitySelector(QualitySelector.from(Quality.HD))
            .build()
    }
    val videoCapture = remember { VideoCapture.withOutput(recorder) }
    val preview = remember {
        Preview.Builder().build().also {
            it.setSurfaceProvider { request -> surfaceRequest = request }
        }
    }
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }

    LaunchedEffect(Unit) {
        cameraProvider = suspendCancellableCoroutine { cont ->
            val future = ProcessCameraProvider.getInstance(context)
            future.addListener(
                {
                    try { cont.resume(future.get()) }
                    catch (e: Exception) { cont.cancel(e) }
                },
                ContextCompat.getMainExecutor(context),
            )
            cont.invokeOnCancellation { future.cancel(true) }
        }
    }

    LaunchedEffect(videoCapture) {
        viewModel.bindVideoCapture(videoCapture)
    }

    LaunchedEffect(cameraProvider, viewModel.uiState.isFrontCamera) {
        val provider = cameraProvider ?: return@LaunchedEffect
        val selector = if (viewModel.uiState.isFrontCamera) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
        provider.unbindAll()
        provider.bindToLifecycle(lifecycleOwner, selector, preview, videoCapture)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is CameraSideEffect.SaveVideoAndNavigateBack -> navigateBack(effect.filePath)
            }
        }
    }

    CameraScreen(
        uiState = viewModel.uiState,
        surfaceRequest = surfaceRequest,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun CameraScreen(
    uiState: CameraUiState,
    surfaceRequest: SurfaceRequest?,
    onEvent: (CameraEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        surfaceRequest?.let { request ->
            CameraXViewfinder(
                surfaceRequest = request,
                modifier = Modifier.fillMaxSize(),
            )
        }
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
