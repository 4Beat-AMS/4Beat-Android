package com.fourbeat.data.repository

import com.fourbeat.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override suspend fun login(email: String): Long {
        return 0L
    }
}
