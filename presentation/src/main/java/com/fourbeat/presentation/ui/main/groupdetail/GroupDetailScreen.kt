package com.fourbeat.presentation.ui.main.groupdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fourbeat.presentation.ui.main.groupdetail.header.GroupDetailHeaderEvent
import com.fourbeat.presentation.ui.main.groupdetail.header.GroupDetailHeaderSideEffect
import com.fourbeat.presentation.ui.main.groupdetail.header.GroupDetailHeaderUiState
import com.fourbeat.presentation.ui.main.groupdetail.header.GroupDetailHeaderViewModel

@Composable
fun GroupDetailRoute(
    modifier: Modifier = Modifier,
    navigateToCreatePost: () -> Unit,
    showGroupCodeDialog: () -> Unit,
    viewModel: GroupDetailHeaderViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                GroupDetailHeaderSideEffect.NavigateToCreatePost -> navigateToCreatePost()
                GroupDetailHeaderSideEffect.ShowGroupCodeDialog -> showGroupCodeDialog()
            }
        }
    }

    GroupDetailScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun GroupDetailScreen(
    modifier: Modifier = Modifier,
    uiState: GroupDetailHeaderUiState,
    onEvent: (GroupDetailHeaderEvent) -> Unit,
) {
}
