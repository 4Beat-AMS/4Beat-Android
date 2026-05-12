package com.fourbeat.domain.repository

import com.fourbeat.domain.model.group.CreateGroupRequest
import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.model.group.MyPostStatus

interface GroupRepository {
    suspend fun createGroup(request: CreateGroupRequest): Group
    suspend fun getMyGroups(): List<Group>
    suspend fun joinGroup(code: String): Group
    suspend fun getGroupInfo(groupId: Long): Group
    suspend fun getGroupPostStatus(groupId: Long): MyPostStatus
}
