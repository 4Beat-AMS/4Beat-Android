package com.fourbeat.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fourbeat.presentation.theme.bold18
import com.fourbeat.presentation.theme.contentPadding

private val topBarHeight = 72.dp

@Composable
fun TopbarForBack(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(topBarHeight),
        contentAlignment = Alignment.CenterStart,
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "ic-back",
            )
        }
    }
}

@Composable
fun TitleTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBack: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(topBarHeight),
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onBack,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "ic-back",
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = bold18,
        )
    }
}

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onPlusClick: () -> Unit,
    onHashClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .padding(horizontal = contentPadding),
    ) {
        Box(
            modifier = Modifier.align(Alignment.CenterStart),
        ) {
            // TODO: 앱 로고
        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            IconButton(onClick = onPlusClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "ic-plus",
                )
            }
            IconButton(onClick = onHashClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "ic-search",
                )
            }
        }
    }
}
