package com.fourbeat.presentation.ui.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.fourbeat.presentation.navigation.MainScreen
import com.fourbeat.presentation.navigation.ScreenGraph
import com.fourbeat.presentation.ui.FourBeatAppState
import com.fourbeat.presentation.ui.main.creategroup.CreateGroupRoute
import com.fourbeat.presentation.ui.main.groupdetail.GroupDetailRoute
import com.fourbeat.presentation.ui.main.home.HomeRoute
import com.fourbeat.presentation.ui.main.joingroup.JoinGroupRoute
import com.fourbeat.presentation.ui.main.selectsong.SelectSongRoute

fun NavGraphBuilder.nestedMainGraph(appState: FourBeatAppState) {
    val navController = appState.naveController

    navigation<ScreenGraph.Main>(startDestination = MainScreen.Home) {
        composable<MainScreen.Home> {
            HomeRoute(
                navigateToCreateGroup = navController::navigateToCreateGroup,
                navigateToJoinGroupDialog = navController::navigateToJoinGroupDialog,
                navigateToGroupDetail = navController::navigateToGroupDetail,
            )
        }
        composable<MainScreen.CreateGroup> {
            CreateGroupRoute(
                navigateToGroupDetail = navController::navigateToGroupDetail,
                navigateToBack = navController::popBackStack,
            )
        }
        dialog<MainScreen.JoinGroupDialog> {
            JoinGroupRoute(
                navigateToGroupDetail = navController::navigateToGroupDetail,
            )
        }
        composable<MainScreen.GroupDetail> {
            GroupDetailRoute(
                navigateToSelectSong = navController::navigateToSelectSong,
                showGroupCodeDialog = navController::navigateToShareGroupCodeDialog,
            )
        }
        dialog<MainScreen.ShareGroupCodeDialog> {

        }
        composable<MainScreen.SelectSong> {
            SelectSongRoute()
        }
        composable<MainScreen.CreatePost> {

        }
    }
}

fun NavController.navigateToMainGraph() = navigate(ScreenGraph.Main) {
    popUpTo(id = graph.id) { inclusive = true }
}

fun NavController.navigateToCreateGroup() = navigate(MainScreen.CreateGroup)

fun NavController.navigateToJoinGroupDialog() = navigate(MainScreen.JoinGroupDialog)

fun NavController.navigateToGroupDetail(groupId: Long) = navigate(MainScreen.GroupDetail(groupId)) {
    popUpTo(MainScreen.Home) { inclusive = false }
}

fun NavController.navigateToShareGroupCodeDialog(code: String) =
    navigate(MainScreen.ShareGroupCodeDialog(code))

fun NavController.navigateToSelectSong(groupId: Long) = navigate(MainScreen.SelectSong(groupId))

fun NavController.navigateToCreatePost(groupId: Long) = navigate(MainScreen.CreatePost(groupId))