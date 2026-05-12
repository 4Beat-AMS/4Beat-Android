package com.fourbeat.presentation.ui.main.creategroup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CreateGroupRoute(
    modifier: Modifier = Modifier,
    navigateToGroupDetail: (Long) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: CreateGroupViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is CreateGroupSideEffect.NavigateToGroupDetail -> navigateToGroupDetail(effect.groupId)
                CreateGroupSideEffect.NavigateToBack -> navigateToBack()
            }
        }
    }

    CreateGroupScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun CreateGroupScreen(
    modifier: Modifier = Modifier,
    uiState: CreateGroupUiState,
    onEvent: (CreateGroupEvent) -> Unit,
) {
}
