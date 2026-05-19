package com.fourbeat.presentation.model.group

import com.fourbeat.domain.model.user.User

data class GroupFeedSlotUiModel(
    val member: User,
    val posts: List<FeedPostUiModel>
)
