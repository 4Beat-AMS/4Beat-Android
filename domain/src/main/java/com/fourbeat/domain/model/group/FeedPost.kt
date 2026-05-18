package com.fourbeat.domain.model.group

import com.fourbeat.domain.model.post.Song

data class FeedPost(
    val id: Long,
    val song: Song,
    val videoUrl: String?,
    val comment: String?,
    val createdAt: String,
)
