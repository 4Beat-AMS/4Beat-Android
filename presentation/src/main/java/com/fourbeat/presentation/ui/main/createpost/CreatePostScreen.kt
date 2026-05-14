package com.fourbeat.presentation.ui.main.createpost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CreatePostRoute(
    modifier: Modifier = Modifier,
    navigateToGroupDetail: () -> Unit,
    navigateToBack: () -> Unit,
    viewModel: CreatePostViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                CreatePostSideEffect.NavigateToBack -> navigateToBack()
                CreatePostSideEffect.CheckCameraPermission -> { /* TODO: 권한 확인 후 OnCameraPermissionResult 이벤트 전달 */ }
                CreatePostSideEffect.NavigateToCamera -> { /* TODO: 카메라 화면 이동 */ }
                CreatePostSideEffect.NavigateToGroupDetail -> navigateToGroupDetail()
            }
        }
    }

    CreatePostScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun CreatePostScreen(
    modifier: Modifier = Modifier,
    uiState: CreatePostUiState,
    onEvent: (CreatePostEvent) -> Unit,
) {

}
