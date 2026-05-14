package com.fourbeat.data.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val name: String,
    val nickname: String,
)
