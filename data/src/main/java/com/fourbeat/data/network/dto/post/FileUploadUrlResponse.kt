package com.fourbeat.data.network.dto.post

import kotlinx.serialization.Serializable

@Serializable
data class FileUploadUrlResponse(
    val uploadUrl: String,
    val videoUrl: String,
)
