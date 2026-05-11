package com.fourbeat.data.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val uid: Long,
)
