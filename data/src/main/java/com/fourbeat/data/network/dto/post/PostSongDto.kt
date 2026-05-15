package com.fourbeat.data.network.dto.post

import kotlinx.serialization.Serializable

@Serializable
data class PostSongDto(
    val title: String,
    val artist: String,
    val imageUrl: String? = null,
)
