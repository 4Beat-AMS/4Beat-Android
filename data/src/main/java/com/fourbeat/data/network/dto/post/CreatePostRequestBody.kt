package com.fourbeat.data.network.dto.post

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequestBody(
    val song: PostSongDto,
    val comment: String? = null,
    val videoUrl: String? = null,
)
