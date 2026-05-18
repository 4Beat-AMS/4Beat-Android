package com.fourbeat.domain.repository

import com.fourbeat.domain.model.user.RegisterRequest
import com.fourbeat.domain.model.user.User

interface UserRepository {
    suspend fun register(request: RegisterRequest): User
}
