package com.fourbeat.presentation.ui.main.joingroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fourbeat.presentation.theme.contentPadding
import com.fourbeat.presentation.ui.component.FourBeatButton
import com.fourbeat.presentation.ui.component.HashTagTextField

@Composable
fun JoinGroupRoute(
    modifier: Modifier = Modifier,
    navigateToGroupDetail: (Long) -> Unit,
    viewModel: JoinGroupViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is JoinGroupSideEffect.NavigateToGroupDetail -> navigateToGroupDetail(effect.groupId)
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = contentPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HashTagTextField(
            value = uiState.code,
            maxLength = JoinGroupUiState.CODE_LENGTH,
            onValueChange = { code -> onEvent(JoinGroupEvent.OnCodeChanged(code)) },
        )
        FourBeatButton(
            isLoading = uiState.isLoading,
            enabled = uiState.isValid,
            text = "참여하기",
            onClick = { onEvent(JoinGroupEvent.OnJoinButtonClicked) },
        )
    }
}
