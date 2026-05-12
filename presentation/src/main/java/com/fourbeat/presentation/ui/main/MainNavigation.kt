package com.fourbeat.presentation.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.fourbeat.presentation.navigation.MainScreen
import com.fourbeat.presentation.navigation.ScreenGraph
import com.fourbeat.presentation.ui.FourBeatAppState

fun NavGraphBuilder.nestedMainGraph(appState: FourBeatAppState) {
    val navController = appState.naveController

    navigation<ScreenGraph.Main>(startDestination = MainScreen.Home) {
        composable<MainScreen.Home> {

        }
        composable<MainScreen.CreateGroup> {

        }
        dialog<MainScreen.JoinGroupDialog> {

        }
        composable<MainScreen.GroupDetail> {

        }
    }
}

fun NavController.navigateToMainGraph() = navigate(ScreenGraph.Main) {
    popUpTo(id = graph.id) { inclusive = true }
}

fun NavController.navigateToCreateGroup() = navigate(MainScreen.CreateGroup)

fun NavController.navigateToJoinGroupDialog() = navigate(MainScreen.JoinGroupDialog)

fun NavController.navigateToGroupDetail(groupId: Long) = navigate(MainScreen.GroupDetail(groupId))