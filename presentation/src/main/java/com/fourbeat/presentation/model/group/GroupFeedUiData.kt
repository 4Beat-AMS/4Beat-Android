package com.fourbeat.presentation.model.group

data class GroupFeedUiData(
    val date: String,
    val previousDate: String?,
    val nextDate: String?,
    val slots: List<GroupFeedSlotUiModel>,
)
