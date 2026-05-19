package com.fourbeat.data.network.dto.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupFeedResponse(
    val date: String,
    val nextDate: String? = null,
    val previousDate: String? = null,
    val slots: List<SlotResponse>,
)
