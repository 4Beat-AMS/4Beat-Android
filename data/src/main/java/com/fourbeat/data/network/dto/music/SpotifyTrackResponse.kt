package com.fourbeat.data.network.dto.music

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyTrackResponse(
    val next: String? = null,
    val previous: String? = null,
    val items: List<SpotifyTrackItemResponse>,
)
