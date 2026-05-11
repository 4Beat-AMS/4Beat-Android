package com.fourbeat.data.network.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestBody(
    val email: String,
    val username: String,
    val nickname: String,
)