package com.fourbeat.domain.model.post

data class CreatePostRequest(
    val song: Song,
    val comment: String?,
    val videoUrl: String?,
)
