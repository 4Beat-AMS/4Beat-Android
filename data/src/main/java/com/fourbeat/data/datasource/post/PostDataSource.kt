package com.fourbeat.data.datasource.post

import com.fourbeat.data.network.dto.post.FileUploadUrlRequestBody
import com.fourbeat.data.network.dto.post.FileUploadUrlResponse

interface PostDataSource {
    suspend fun getFileUploadUrl(body: FileUploadUrlRequestBody): FileUploadUrlResponse
}
