package com.fourbeat.data.network.dto.music

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyAlbumResponse(
    val images: List<SpotifyAlbumImageResponse>,
)
