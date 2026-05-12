package com.fourbeat.data.mapper

import com.fourbeat.data.network.dto.group.CreateGroupRequestBody
import com.fourbeat.data.network.dto.group.GroupResponse
import com.fourbeat.data.network.dto.group.MyPostStatusResponse
import com.fourbeat.domain.model.group.CreateGroupRequest
import com.fourbeat.domain.model.group.Group
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
