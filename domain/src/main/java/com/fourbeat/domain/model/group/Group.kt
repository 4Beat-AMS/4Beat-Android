package com.fourbeat.domain.model.group

data class Group(
    val id: Long,
    val name: String,
    val code: String,
    val maxMemberCount: Int,
    val memberCount: Int,
)
