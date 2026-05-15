package com.fourbeat.data.mapper

import com.fourbeat.data.network.dto.user.RegisterRequestBody
import com.fourbeat.data.network.dto.user.UserDto
import com.fourbeat.domain.model.user.User
import com.fourbeat.domain.model.user.RegisterRequest

fun RegisterRequest.asBody(): RegisterRequestBody =
    RegisterRequestBody(
        email = email,
        username = name,
        nickname = nickname,
    )

fun UserDto.toDomain(): User =
    User(
        id = id,
        name = name,
        nickname = nickname,
    )
