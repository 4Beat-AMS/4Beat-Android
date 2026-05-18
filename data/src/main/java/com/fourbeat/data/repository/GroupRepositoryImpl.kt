package com.fourbeat.data.repository

import com.fourbeat.data.database.dao.GroupFeedMetaDao
import com.fourbeat.data.database.dao.PostDao
import com.fourbeat.data.database.dao.SlotDao
import com.fourbeat.data.database.entity.PostEntity
import com.fourbeat.data.database.entity.PostStatus
import com.fourbeat.data.database.entity.SlotEntity
import com.fourbeat.data.datasource.group.GroupDataSource
import com.fourbeat.data.mapper.asBody
import com.fourbeat.data.mapper.toDomain
import com.fourbeat.data.mapper.toGroupFeed
import com.fourbeat.data.mapper.toMetaEntity
import com.fourbeat.data.mapper.toPostEntity
import com.fourbeat.data.mapper.toSlotEntity
import com.fourbeat.data.network.dto.group.GroupResponse
import com.fourbeat.domain.model.group.CreateGroupRequest
import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.model.group.GroupFeed
import com.fourbeat.domain.model.group.MyPostStatus
import com.fourbeat.domain.model.post.CreatePostRequest
import com.fourbeat.domain.model.post.Post
import com.fourbeat.domain.model.user.User
import com.fourbeat.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupDataSource,
    private val postDao: PostDao,
    private val slotDao: SlotDao,
    private val groupFeedMetaDao: GroupFeedMetaDao,
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

    override suspend fun createPost(groupId: Long, request: CreatePostRequest): Post =
        groupDataSource.createPost(groupId = groupId, body = request.asBody()).toDomain()

    override suspend fun getGroupFeed(groupId: Long, date: String): GroupFeed =
        groupDataSource.getGroupFeed(groupId = groupId, date = date).toDomain()

    override fun observeGroupFeed(groupId: Long, date: String): Flow<GroupFeed> =
        combine(
            slotDao.observeByGroupAndDate(groupId, date),
            groupFeedMetaDao.observe(groupId, date),
        ) { slots, meta ->
            slots.toGroupFeed(meta, date)
        }

    override suspend fun refreshGroupFeed(groupId: Long, date: String) {
        val response = groupDataSource.getGroupFeed(groupId, date)
        slotDao.deleteByGroupAndDate(groupId, date)
        groupFeedMetaDao.upsert(response.toMetaEntity(groupId))
        response.slots.forEach { slot ->
            val slotId = slotDao.insert(slot.toSlotEntity(groupId, date))
            postDao.insertAll(slot.posts.map { it.toPostEntity(slotId) })
        }
    }

    override suspend fun insertOptimisticPost(
        groupId: Long,
        date: String,
        member: User,
        request: CreatePostRequest,
        filePath: String,
    ): Long {
        val tempId = -System.currentTimeMillis()
        val slotId = slotDao.upsert(
            SlotEntity(
                groupId = groupId,
                date = date,
                memberId = member.id,
                memberName = member.name,
                memberNickname = member.nickname,
                slotOrder = slotDao.findByGroupDateMember(groupId, date, member.id)?.slotOrder ?: Int.MAX_VALUE,
            )
        )
        postDao.insert(
            PostEntity(
                id = tempId,
                slotId = slotId,
                songTitle = request.song.title,
                songArtist = request.song.artist,
                albumImageUrl = request.song.albumImageUrl,
                filePath = filePath,
                videoUrl = null,
                comment = request.comment,
                status = PostStatus.PENDING,
            )
        )
        return tempId
    }

    override suspend fun rollbackPost(tempId: Long) {
        postDao.delete(tempId)
    }

    override suspend fun confirmPost(tempId: Long, post: Post) {
        val pending = postDao.getById(tempId) ?: return
        postDao.confirmPost(
            tempId = tempId,
            realEntity = pending.copy(
                id = post.id,
                videoUrl = post.videoUrl,
                filePath = null,
                createdAt = post.createdAt,
                status = PostStatus.STABLE,
            )
        )
    }
}
