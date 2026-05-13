package com.fourbeat.presentation.mapper

import com.fourbeat.domain.model.group.Group
import com.fourbeat.domain.model.group.MyPostStatus
import com.fourbeat.presentation.model.group.GroupUiModel
import com.fourbeat.presentation.model.group.MyPostStatusUiModel

fun Group.toUiModel(): GroupUiModel =
    GroupUiModel(
        id = id,
        name = name,
        code = code,
        capacity = "${memberCount}명/${maxMemberCount}명",
    )

fun MyPostStatus.toUiModel(): MyPostStatusUiModel =
    MyPostStatusUiModel(
        status = if (canPost) {
            "오늘 ${totalPostLimit}회 중에 ${remainingPostCount}회 남았어요"
        } else {
            "오늘 ${totalPostLimit}회의 할당량을 모두 소진했어요"
        },
        canPost = canPost
    )
