package com.fourbeat.data.datasource.post

import com.fourbeat.data.network.di.PrivateNetwork
import com.fourbeat.data.network.dto.post.FileUploadUrlRequestBody
import com.fourbeat.data.network.dto.post.FileUploadUrlResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRemoteDataSource @Inject constructor(
    @param:PrivateNetwork
    private val client: HttpClient,
) : PostDataSource {
    override suspend fun getFileUploadUrl(body: FileUploadUrlRequestBody): FileUploadUrlResponse =
        client
            .post("posts/video-upload-url") {
                setBody(body)
            }.body()
}
