package com.fourbeat.data.network.dto.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupResponse(
    val id: Long,
    val name: String,
    val code: String,
    val maxMemberCount: Int,
    val memberCount: Int,
)
