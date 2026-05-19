package com.fourbeat.domain.model.group

data class GroupFeed(
    val date: String,
    val nextDate: String?,
    val previousDate: String?,
    val slots: List<GroupFeedSlot>,
)
