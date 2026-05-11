package com.fourbeat.data.mapper

import com.fourbeat.data.network.dto.user.RegisterRequestBody
import com.fourbeat.domain.model.user.RegisterRequest

fun RegisterRequest.asBody(): RegisterRequestBody =
    RegisterRequestBody(
        email = email,
        name = name,
        nickname = nickname,
    )
