package com.fourbeat.data.network.dto.group

import kotlinx.serialization.Serializable

@Serializable
data class CreateGroupRequestBody(
    val name: String,
    val maxMemberCount: Int,
)
