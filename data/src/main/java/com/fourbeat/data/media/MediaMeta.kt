package com.fourbeat.data.media

import kotlinx.serialization.Serializable

@Serializable
data class MediaMeta(
    val title: String?,
    val artist: String?,
    val albumArtUri: String?,
)
