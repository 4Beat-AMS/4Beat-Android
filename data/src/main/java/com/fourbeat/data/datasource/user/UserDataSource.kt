package com.fourbeat.data.datasource.user

import com.fourbeat.data.network.dto.user.RegisterRequestBody
import com.fourbeat.data.network.dto.user.UserResponse

interface UserDataSource {
    suspend fun register(body: RegisterRequestBody): UserResponse
}
