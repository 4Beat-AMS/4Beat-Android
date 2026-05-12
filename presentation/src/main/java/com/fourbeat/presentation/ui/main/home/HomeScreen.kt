package com.fourbeat.presentation.ui.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    navigateToCreateGroup: () -> Unit,
    navigateToJoinGroupDialog: () -> Unit,
    navigateToGroupDetail: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                HomeSideEffect.NavigateToCreateGroup -> navigateToCreateGroup()
                HomeSideEffect.NavigateToJoinGroupDialog -> navigateToJoinGroupDialog()
                is HomeSideEffect.NavigateToGroupDetail -> navigateToGroupDetail(effect.groupId)
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
) {
}
