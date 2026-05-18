package com.fourbeat.domain.repository

import com.fourbeat.domain.model.group.CreateGroupRequest
import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.model.group.GroupFeed
import com.fourbeat.domain.model.group.MyPostStatus
import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.post.Post

interface GroupRepository {
    suspend fun createGroup(request: CreateGroupRequest): Group
    suspend fun getMyGroups(): List<Group>
    suspend fun joinGroup(code: String): Group
    suspend fun getGroupInfo(groupId: Long): Group
    suspend fun getGroupPostStatus(groupId: Long): MyPostStatus
    suspend fun createPost(groupId: Long, request: CreatePostRequest): Post
    suspend fun getGroupFeed(groupId: Long, date: String): GroupFeed
}
