package com.fourbeat.data.mapper

import com.fourbeat.data.database.entity.GroupFeedMetaEntity
import com.fourbeat.data.database.entity.PostEntity
import com.fourbeat.data.database.entity.PostStatus
import com.fourbeat.data.database.entity.SlotEntity
import com.fourbeat.data.database.entity.SlotWithPosts
import com.fourbeat.data.network.dto.group.CreateGroupRequestBody
import com.fourbeat.data.network.dto.group.GroupFeedResponse
import com.fourbeat.data.network.dto.group.GroupResponse
import com.fourbeat.data.network.dto.group.MyPostStatusResponse
import com.fourbeat.data.network.dto.group.SlotPostResponse
import com.fourbeat.data.network.dto.group.SlotResponse
import com.fourbeat.domain.model.group.CreateGroupRequest
import com.fourbeat.domain.model.group.FeedPost
import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.model.group.GroupFeed
import com.fourbeat.domain.model.group.GroupFeedSlot
import com.fourbeat.domain.model.group.MyPostStatus
import com.fourbeat.domain.model.user.User

fun GroupResponse.toDomain(): Group =
    Group(
        id = id,
        name = name,
        code = code,
        maxMemberCount = maxMemberCount,
        memberCount = memberCount,
    )

fun MyPostStatusResponse.toDomain(): MyPostStatus =
    MyPostStatus(
        remainingPostCount = remainingPostCount,
        totalPostLimit = totalPostLimit,
        canPost = canPost,
    )

fun CreateGroupRequest.asBody(): CreateGroupRequestBody =
    CreateGroupRequestBody(
        name = name,
        maxMemberCount = maxMemberCount.value,
    )

fun GroupFeedResponse.toDomain(): GroupFeed =
    GroupFeed(
        date = date,
        nextDate = nextDate,
        previousDate = previousDate,
        slots = slots.map(SlotResponse::toDomain).sorted(),
    )

fun SlotResponse.toDomain(): GroupFeedSlot =
    GroupFeedSlot(
        order = order,
        member = member.toDomain(),
        posts = posts.map(SlotPostResponse::toDomain).sorted(),
    )

fun SlotPostResponse.toDomain(): FeedPost =
    FeedPost(
        id = id,
        song = song.toDomain(),
        videoUrl = videoUrl,
        comment = comment,
        createdAt = createdAt,
    )

fun GroupFeedResponse.toMetaEntity(groupId: Long): GroupFeedMetaEntity =
    GroupFeedMetaEntity(
        groupId = groupId,
        date = date,
        nextDate = nextDate,
        previousDate = previousDate,
    )

fun SlotResponse.toSlotEntity(groupId: Long, date: String): SlotEntity =
    SlotEntity(
        groupId = groupId,
        date = date,
        memberId = member.id,
        memberName = member.name,
        memberNickname = member.nickname,
        slotOrder = order,
    )

fun SlotPostResponse.toPostEntity(slotId: Long): PostEntity =
    PostEntity(
        id = id,
        slotId = slotId,
        songTitle = song.title,
        songArtist = song.artist,
        albumImageUrl = song.imageUrl,
        filePath = null,
        videoUrl = videoUrl,
        comment = comment,
        createdAt = createdAt,
        status = PostStatus.STABLE,
    )

fun SlotWithPosts.toDomain(): GroupFeedSlot =
    GroupFeedSlot(
        order = slot.slotOrder,
        member = User(id = slot.memberId, name = slot.memberName, nickname = slot.memberNickname),
        posts = posts.map { it.toDomain() }.sorted(),
    )

fun List<SlotWithPosts>.toGroupFeed(meta: GroupFeedMetaEntity?, date: String): GroupFeed =
    GroupFeed(
        date = date,
        nextDate = meta?.nextDate,
        previousDate = meta?.previousDate,
        slots = map { it.toDomain() }.sorted(),
    )
