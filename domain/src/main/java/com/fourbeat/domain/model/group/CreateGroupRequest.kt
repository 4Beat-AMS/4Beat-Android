package com.fourbeat.domain.model.group

data class CreateGroupRequest(
    val name: String,
    val maxMemberCount: GroupMemberCount,
)
