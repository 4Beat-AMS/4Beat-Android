package com.fourbeat.domain.repository

import com.fourbeat.domain.model.user.RegisterRequest

interface UserRepository {
    suspend fun register(request: RegisterRequest): Long
}
