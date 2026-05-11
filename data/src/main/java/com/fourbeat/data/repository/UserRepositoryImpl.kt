package com.fourbeat.data.repository

import com.fourbeat.data.datasource.user.UserDataSource
import com.fourbeat.data.mapper.asBody
import com.fourbeat.domain.model.user.RegisterRequest
import com.fourbeat.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun register(request: RegisterRequest): Long =
        userDataSource
            .register(request.asBody())
            .uid
}
