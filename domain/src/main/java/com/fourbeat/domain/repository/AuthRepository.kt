package com.fourbeat.domain.repository

interface AuthRepository {
    suspend fun login(email: String): Long
}
