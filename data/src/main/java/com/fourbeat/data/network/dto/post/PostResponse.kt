package com.fourbeat.data.network.dto.post

import com.fourbeat.data.network.dto.user.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val id: Long,
    val author: UserDto,
    val song: PostSongDto,
    val videoUrl: String? = null,
    val comment: String? = null,
    val createdAt: String,
)
