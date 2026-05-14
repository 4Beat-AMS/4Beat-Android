package com.fourbeat.domain.model.post

import com.fourbeat.domain.model.user.User

data class Post(
    val id: Long,
    val author: User,
    val song: Song,
    val videoUrl: String?,
    val comment: String?,
    val createdAt: String,
)
