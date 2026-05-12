package com.fourbeat.presentation.ui.main.joingroup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun JoinGroupRoute(
    modifier: Modifier = Modifier,
    navigateToGroupDetail: (Long) -> Unit,
    dismiss: () -> Unit,
    viewModel: JoinGroupViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is JoinGroupSideEffect.NavigateToGroupDetail -> {
                    dismiss()
                    navigateToGroupDetail(effect.groupId)
                }
                JoinGroupSideEffect.Dismiss -> dismiss()
            }
        }
    }

    JoinGroupDialog(
        modifier = modifier,
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun JoinGroupDialog(
    modifier: Modifier = Modifier,
    uiState: JoinGroupUiState,
    onEvent: (JoinGroupEvent) -> Unit,
) {
}
