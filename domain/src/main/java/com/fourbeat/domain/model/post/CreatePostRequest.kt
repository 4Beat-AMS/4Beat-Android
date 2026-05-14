package com.fourbeat.domain.model.post

import java.io.File

data class CreatePostRequest(
    val song: Song,
    val comment: String?,
    val videoFile: File?,
)
