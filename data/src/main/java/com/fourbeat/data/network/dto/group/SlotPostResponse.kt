package com.fourbeat.data.network.dto.group

import com.fourbeat.data.network.dto.post.PostSongDto
import kotlinx.serialization.Serializable

@Serializable
data class SlotPostResponse(
    val id: Long,
    val song: PostSongDto,
    val videoUrl: String? = null,
    val comment: String? = null,
    val createdAt: String,
)
