package com.fourbeat.data.datasource.user

import com.fourbeat.data.network.di.PublicNetwork
import com.fourbeat.data.network.dto.user.RegisterRequestBody
import com.fourbeat.data.network.dto.user.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
    @param:PublicNetwork
    private val client: HttpClient,
) : UserDataSource {
    override suspend fun register(body: RegisterRequestBody): UserResponse =
        client
            .post("users/register") {
                setBody(body)
            }.body()
}
