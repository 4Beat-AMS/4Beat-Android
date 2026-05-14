package com.fourbeat.presentation.ui.main.groupdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fourbeat.presentation.ui.main.groupdetail.header.GroupDetailHeader

@Composable
fun GroupDetailRoute(
    modifier: Modifier = Modifier,
    navigateToSelectSong: (Long) -> Unit,
    showGroupCodeDialog: (String) -> Unit,
) {
    GroupDetailScreen(
        modifier = modifier,
        navigateToSelectSong = navigateToSelectSong,
        showGroupCodeDialog = showGroupCodeDialog
    )
}

@Composable
private fun GroupDetailScreen(
    modifier: Modifier,
    navigateToSelectSong: (Long) -> Unit,
    showGroupCodeDialog: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GroupDetailHeader(
            navigateToSelectSong = navigateToSelectSong,
            showGroupCodeDialog = showGroupCodeDialog,
        )
    }
}
