package com.fourbeat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.fourbeat.presentation.ui.FourBeatAppState
import com.fourbeat.presentation.ui.auth.nestedAuthGraph

@Composable
fun FourBeatNavHost(
    modifier: Modifier = Modifier,
    appState: FourBeatAppState,
) {
    NavHost(
        modifier = modifier,
        navController = appState.naveController,
        startDestination = ScreenGraph.Auth
    ) {
        nestedAuthGraph(appState = appState)
    }
}