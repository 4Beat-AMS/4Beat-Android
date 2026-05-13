package com.fourbeat.data.datasource.auth

import com.fourbeat.data.network.di.PublicNetwork
import com.fourbeat.data.network.dto.auth.LoginRequestBody
import com.fourbeat.data.network.dto.user.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSource @Inject constructor(
    @param:PublicNetwork
    private val client: HttpClient,
) : AuthDataSource {
    override suspend fun login(body: LoginRequestBody): UserResponse =
        client
            .post("auth/login") {
                setBody(body)
            }.body()
}
