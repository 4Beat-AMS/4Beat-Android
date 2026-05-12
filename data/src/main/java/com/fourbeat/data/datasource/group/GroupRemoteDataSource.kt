package com.fourbeat.data.datasource.group

import com.fourbeat.data.network.di.DefaultNetwork
import com.fourbeat.data.network.dto.group.CreateGroupRequestBody
import com.fourbeat.data.network.dto.group.GroupResponse
import com.fourbeat.data.network.dto.group.MyPostStatusResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRemoteDataSource @Inject constructor(
    @param:DefaultNetwork
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
            .get("groups/$groupId/post/status")
            .body()
}
