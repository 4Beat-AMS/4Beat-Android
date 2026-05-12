package com.fourbeat.data.datasource.group

import com.fourbeat.data.network.dto.group.CreateGroupRequestBody
import com.fourbeat.data.network.dto.group.GroupResponse
import com.fourbeat.data.network.dto.group.MyPostStatusResponse

interface GroupDataSource {
    suspend fun createGroup(body: CreateGroupRequestBody): GroupResponse
    suspend fun getMyGroups(): List<GroupResponse>
    suspend fun joinGroup(code: String): GroupResponse
    suspend fun getGroupInfo(groupId: Long): GroupResponse
    suspend fun getGroupPostStatus(groupId: Long): MyPostStatusResponse
}
