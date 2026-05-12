package com.fourbeat.data.network.dto.group

import kotlinx.serialization.Serializable

@Serializable
data class MyPostStatusResponse(
    val remainingPostCount: Int,
    val totalPostLimit: Int,
    val canPost: Boolean,
)
