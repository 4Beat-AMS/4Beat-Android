package com.fourbeat.presentation.model.group

import com.fourbeat.domain.model.post.Song

data class FeedPostUiModel(
    val id: Long,
    val song: Song,
    val videoUrl: String?,
    val comment: String?,
    val createdAt: String,
)
