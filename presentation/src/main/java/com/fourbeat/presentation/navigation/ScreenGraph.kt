package com.fourbeat.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenGraph {
    @Serializable
    data object Auth : ScreenGraph

    @Serializable
    data object Main : ScreenGraph
}