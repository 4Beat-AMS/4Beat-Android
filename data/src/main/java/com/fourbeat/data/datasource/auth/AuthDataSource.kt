package com.fourbeat.data.datasource.auth

import com.fourbeat.data.network.dto.auth.LoginRequestBody
import com.fourbeat.data.network.dto.user.UserResponse

interface AuthDataSource {
    suspend fun login(body: LoginRequestBody): UserResponse
}
