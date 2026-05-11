package com.fourbeat.presentation.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fourbeat.presentation.navigation.MainScreen
import com.fourbeat.presentation.navigation.ScreenGraph
import com.fourbeat.presentation.ui.FourBeatAppState

fun NavGraphBuilder.nestedMainGraph(appState: FourBeatAppState) {
    val navController = appState.naveController

    navigation<ScreenGraph.Main>(startDestination = MainScreen.Home) {
        composable<MainScreen.Home> {

        }
    }
}

fun NavController.navigateToMainGraph() = navigate(ScreenGraph.Main) {
    popUpTo(id = graph.id) { inclusive = true }
}