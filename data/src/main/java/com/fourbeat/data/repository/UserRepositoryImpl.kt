package com.fourbeat.data.repository

import com.fourbeat.domain.model.user.RegisterRequest
import com.fourbeat.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {
    override suspend fun register(request: RegisterRequest): Long {
        TODO("Not yet implemented")
    }
}
