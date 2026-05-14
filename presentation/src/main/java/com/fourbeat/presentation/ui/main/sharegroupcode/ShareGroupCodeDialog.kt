package com.fourbeat.presentation.ui.main.sharegroupcode

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fourbeat.presentation.theme.contentPadding
import com.fourbeat.presentation.ui.component.FourBeatButton
import com.fourbeat.presentation.ui.component.HashTagTextField
import com.fourbeat.presentation.ui.main.joingroup.JoinGroupUiState

@Composable
fun ShareGroupCodeRoute(
    modifier: Modifier = Modifier,
    viewModel: ShareGroupCodeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ShareGroupCodeSideEffect.ShareCode -> {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, effect.code)
                    }
                    context.startActivity(Intent.createChooser(intent, null))
                }
            }
        }
    }

    ShareGroupCodeDialog(
        modifier = modifier,
        code = viewModel.code,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun ShareGroupCodeDialog(
    modifier: Modifier = Modifier,
    code: String,
    onEvent: (ShareGroupCodeEvent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = contentPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HashTagTextField(
            value = code,
            maxLength = JoinGroupUiState.CODE_LENGTH,
            enabled = false,
            onValueChange = {/*UNUSED*/},
        )
        FourBeatButton(
            isLoading = false,
            enabled = true,
            text = "공유하기",
            onClick = { onEvent(ShareGroupCodeEvent.OnShareButtonClicked) },
        )
    }
}
