package com.fourbeat.domain.repository

import com.fourbeat.domain.model.user.User

interface AuthRepository {
    suspend fun login(email: String): User
}
