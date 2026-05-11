package com.fourbeat.presentation.ui.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fourbeat.presentation.theme.White

@Composable
fun RegisterRoute(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is RegisterSideEffect.NavigateToHome -> navigateToHome()
                RegisterSideEffect.NavigateToBack -> onBack()
            }
        }
    }

    RegisterScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun RegisterScreen(
    modifier: Modifier,
    uiState: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
    }
}
