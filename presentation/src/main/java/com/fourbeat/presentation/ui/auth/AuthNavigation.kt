package com.fourbeat.presentation.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fourbeat.presentation.model.auth.OAuthUser
import com.fourbeat.presentation.navigation.AuthScreen
import com.fourbeat.presentation.navigation.ScreenGraph
import com.fourbeat.presentation.ui.FourBeatAppState
import com.fourbeat.presentation.ui.auth.login.LoginRoute
import com.fourbeat.presentation.ui.auth.register.RegisterRoute
import com.fourbeat.presentation.ui.main.navigateToMainGraph

fun NavGraphBuilder.nestedAuthGraph(appState: FourBeatAppState) {
    val navController = appState.naveController

    navigation<ScreenGraph.Auth>(startDestination = AuthScreen.Login) {
        composable<AuthScreen.Login> {
            LoginRoute(
                navigateToHome = navController::navigateToMainGraph,
                navigateToRegister = navController::navigateToRegister
            )
        }
        composable<AuthScreen.Register> {
            RegisterRoute(
                navigateToHome = navController::navigateToMainGraph,
                onBack = navController::popBackStack
            )
        }
    }
}

fun NavController.navigateToAuthGraph() = navigate(ScreenGraph.Auth) {
    popUpTo(id = graph.id) { inclusive = true }
}

fun NavController.navigateToRegister(oAuthUser: OAuthUser) =
    navigate(AuthScreen.Register(oAuthUser.email, oAuthUser.nickname))