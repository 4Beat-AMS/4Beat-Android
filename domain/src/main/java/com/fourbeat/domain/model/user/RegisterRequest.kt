package com.fourbeat.domain.model.user

data class RegisterRequest(
    val email: String,
    val name: String,
    val nickname: String,
)
