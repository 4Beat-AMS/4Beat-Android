package com.fourbeat.data.network.dto.post

import kotlinx.serialization.Serializable

@Serializable
data class FileUploadUrlRequestBody(
    val fileName: String,
    val contentType: String,
)
