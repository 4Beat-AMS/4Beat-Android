package com.fourbeat.presentation.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fourbeat.presentation.navigation.AuthScreen
import com.fourbeat.presentation.navigation.ScreenGraph
import com.fourbeat.presentation.ui.FourBeatAppState

fun NavGraphBuilder.nestedAuthGraph(appState: FourBeatAppState) {
    val navController = appState.naveController

    navigation<ScreenGraph.Auth>(startDestination = AuthScreen.Login) {
        composable<AuthScreen.Login> {

        }
        composable<AuthScreen.Register> {

        }
    }
}

fun NavController.navigateToAuthGraph() = navigate(ScreenGraph.Auth) {
    popUpTo(id = graph.id, { inclusive = true })
}

fun NavController.navigateToRegisterScreen(email: String) =
    navigate(AuthScreen.Register(email))