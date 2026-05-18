package com.fourbeat.domain.model.group

import com.fourbeat.domain.model.user.User

data class GroupFeedSlot(
    val order: Int,
    val member: User,
    val posts: List<FeedPost>,
) : Comparable<GroupFeedSlot> {
    override fun compareTo(other: GroupFeedSlot): Int {
        return order - other.order
    }
}
