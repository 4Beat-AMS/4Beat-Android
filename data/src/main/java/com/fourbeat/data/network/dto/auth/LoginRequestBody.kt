package com.fourbeat.data.network.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestBody(val email: String)
