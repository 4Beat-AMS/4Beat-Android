package com.fourbeat.data.network.dto.group

import com.fourbeat.data.network.dto.user.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class SlotResponse(
    val order: Int,
    val member: UserDto,
    val posts: List<SlotPostResponse>,
)
