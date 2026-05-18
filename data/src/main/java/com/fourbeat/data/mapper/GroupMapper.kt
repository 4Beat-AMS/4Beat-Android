package com.fourbeat.data.mapper

import com.fourbeat.data.database.entity.PostEntity
import com.fourbeat.data.database.entity.PostStatus
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

fun GroupFeedResponse.toEntities(groupId: Long): List<PostEntity> =
    slots.flatMap { slot ->
        slot.posts.map { post ->
            PostEntity(
                id = post.id,
                groupId = groupId,
                date = date,
                memberId = slot.member.id,
                memberName = slot.member.name,
                memberNickname = slot.member.nickname,
                slotOrder = slot.order,
                songTitle = post.song.title,
                songArtist = post.song.artist,
                albumImageUrl = post.song.imageUrl,
                filePath = null,
                videoUrl = post.videoUrl,
                comment = post.comment,
                createdAt = post.createdAt,
                status = PostStatus.STABLE,
                workId = null,
                nextDate = nextDate,
                previousDate = previousDate,
            )
        }
    }

fun List<PostEntity>.toGroupFeed(date: String): GroupFeed {
    val nextDate = firstOrNull()?.nextDate
    val previousDate = firstOrNull()?.previousDate
    val slots = groupBy { it.memberId }
        .map { (memberId, entities) ->
            val first = entities.first()
            GroupFeedSlot(
                order = first.slotOrder,
                member = User(id = memberId, name = first.memberName, nickname = first.memberNickname),
                posts = entities.map(PostEntity::toDomain).sorted(),
            )
        }
        .sorted()
    return GroupFeed(date = date, nextDate = nextDate, previousDate = previousDate, slots = slots)
}
