package com.fourbeat.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Stable
data class FourBeatAppState(
    val naveController: NavHostController,
    val coroutineScope: CoroutineScope,
)

@Composable
fun rememberFourBeatAppState(
    naveController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) : FourBeatAppState =
    remember(naveController, coroutineScope) {
        FourBeatAppState(naveController, coroutineScope)
    }
