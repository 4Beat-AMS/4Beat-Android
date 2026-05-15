package com.fourbeat.domain.repository

import com.fourbeat.domain.model.post.FileUploadUrl
import com.fourbeat.domain.model.post.FileUploadUrlRequest

interface PostRepository {
    suspend fun getFileUploadUrl(request: FileUploadUrlRequest): FileUploadUrl
}
