package com.fourbeat.domain.model.group

data class MyPostStatus(
    val remainingPostCount: Int,
    val totalPostLimit: Int,
    val canPost: Boolean,
)
