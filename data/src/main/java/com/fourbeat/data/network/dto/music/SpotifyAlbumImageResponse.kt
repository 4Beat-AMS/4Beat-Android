package com.fourbeat.data.network.dto.music

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyAlbumImageResponse(
    val url: String,
    val height: Int,
    val width: Int,
)
