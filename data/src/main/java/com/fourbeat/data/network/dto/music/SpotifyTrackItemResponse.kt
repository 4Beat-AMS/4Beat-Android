package com.fourbeat.data.network.dto.music

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyTrackItemResponse(
    val name: String,
    val album: SpotifyAlbumResponse,
    val artists: List<SpotifyArtistResponse>,
)
