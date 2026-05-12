package com.fourbeat.data.repository

import com.fourbeat.data.datasource.group.GroupDataSource
import com.fourbeat.data.mapper.asBody
import com.fourbeat.data.mapper.toDomain
import com.fourbeat.data.network.dto.group.GroupResponse
import com.fourbeat.domain.model.group.CreateGroupRequest
import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.model.group.MyPostStatus
import com.fourbeat.domain.repository.GroupRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupDataSource,
) : GroupRepository {
    override suspend fun createGroup(request: CreateGroupRequest): Group =
        groupDataSource.createGroup(request.asBody()).toDomain()

    override suspend fun getMyGroups(): List<Group> =
        groupDataSource.getMyGroups().map(GroupResponse::toDomain)

    override suspend fun joinGroup(code: String): Group =
        groupDataSource.joinGroup(code).toDomain()

    override suspend fun getGroupInfo(groupId: Long): Group =
        groupDataSource.getGroupInfo(groupId).toDomain()

    override suspend fun getGroupPostStatus(groupId: Long): MyPostStatus =
        groupDataSource.getGroupPostStatus(groupId).toDomain()
}
