package com.fourbeat.presentation.ui.main.groupdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fourbeat.presentation.ui.main.groupdetail.header.GroupDetailHeader

@Composable
fun GroupDetailRoute(
    modifier: Modifier = Modifier,
    navigateToCreatePost: () -> Unit,
    showGroupCodeDialog: () -> Unit,
) {
    GroupDetailScreen(
        modifier = modifier,
        navigateToCreatePost = navigateToCreatePost,
        showGroupCodeDialog = showGroupCodeDialog
    )
}

@Composable
private fun GroupDetailScreen(
    modifier: Modifier,
    navigateToCreatePost: () -> Unit,
    showGroupCodeDialog: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GroupDetailHeader(
            navigateToCreatePost = navigateToCreatePost,
            showGroupCodeDialog = showGroupCodeDialog,
        )
    }
}
