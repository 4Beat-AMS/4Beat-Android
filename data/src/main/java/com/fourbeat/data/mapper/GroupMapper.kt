package com.fourbeat.data.mapper

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
        slots = slots.map(SlotResponse::toDomain),
    )

fun SlotResponse.toDomain(): GroupFeedSlot =
    GroupFeedSlot(
        order = order,
        member = member.toDomain(),
        posts = posts.map(SlotPostResponse::toDomain),
    )

fun SlotPostResponse.toDomain(): FeedPost =
    FeedPost(
        id = id,
        song = song.toDomain(),
        videoUrl = videoUrl,
        comment = comment,
        createdAt = createdAt,
    )
