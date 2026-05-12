package com.fourbeat.presentation.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fourbeat.presentation.theme.Gray700
import com.fourbeat.presentation.theme.normal16

@Composable
fun FourBeatLabel(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        style = normal16,
        color = Gray700,
    )
}
