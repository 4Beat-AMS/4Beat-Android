package com.fourbeat.data.network.dto.music

import kotlinx.serialization.Serializable

@Serializable
data class SpotifySearchResponse(
    val tracks: SpotifyTrackResponse,
)
