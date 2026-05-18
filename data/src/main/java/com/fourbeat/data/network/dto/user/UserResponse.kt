package com.fourbeat.data.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val username: String,
    val nickname: String,
)
