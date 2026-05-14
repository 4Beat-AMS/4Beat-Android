package com.fourbeat.data.datasource.group

import com.fourbeat.data.network.di.PrivateNetwork
import com.fourbeat.data.network.dto.group.CreateGroupRequestBody
import com.fourbeat.data.network.dto.group.GroupResponse
import com.fourbeat.data.network.dto.group.MyPostStatusResponse
import com.fourbeat.data.network.dto.post.CreatePostRequestBody
import com.fourbeat.data.network.dto.post.PostResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.utils.io.streams.asInput
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRemoteDataSource @Inject constructor(
    @param:PrivateNetwork
    private val client: HttpClient,
) : GroupDataSource {
    override suspend fun createGroup(body: CreateGroupRequestBody): GroupResponse =
        client
            .post("groups") {
                setBody(body)
            }.body()

    override suspend fun getMyGroups(): List<GroupResponse> =
        client
            .get("groups")
            .body()

    override suspend fun joinGroup(code: String): GroupResponse =
        client
            .post("groups/join") {
                parameter("code", code)
            }.body()

    override suspend fun getGroupInfo(groupId: Long): GroupResponse =
        client
            .get("groups/$groupId")
            .body()

    override suspend fun getGroupPostStatus(groupId: Long): MyPostStatusResponse =
        client
            .get("groups/$groupId/posts/status")
            .body()

    override suspend fun createPost(
        groupId: Long,
        body: CreatePostRequestBody,
        videoFile: File?,
    ): PostResponse =
        client
            .post("groups/$groupId/posts") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                key = "data",
                                value = Json.encodeToString(body),
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, "application/json")
                                },
                            )
                            if (videoFile != null) {
                                appendInput(
                                    key = "videoFile",
                                    headers = Headers.build {
                                        append(HttpHeaders.ContentType, "video/mp4")
                                        append(HttpHeaders.ContentDisposition, "filename=\"${videoFile.name}\"")
                                    },
                                    size = videoFile.length(),
                                ) { videoFile.inputStream().asInput() }
                            }
                        }
                    )
                )
            }
                .body()
}
