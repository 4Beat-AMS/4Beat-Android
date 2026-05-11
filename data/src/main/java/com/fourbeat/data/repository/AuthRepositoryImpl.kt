package com.fourbeat.data.repository

import com.fourbeat.data.datasource.auth.AuthDataSource
import com.fourbeat.data.network.dto.auth.LoginRequestBody
import com.fourbeat.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(email: String): Long =
        authDataSource
            .login(LoginRequestBody(email))
            .uid
}
