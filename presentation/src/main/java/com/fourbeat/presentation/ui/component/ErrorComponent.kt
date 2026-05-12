package com.fourbeat.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorComponent(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = onRefresh) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "ic-refresh",
            )
        }
    }
}
