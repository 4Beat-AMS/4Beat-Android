package com.fourbeat.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SlotWithPosts(
    @Embedded val slot: SlotEntity,
    @Relation(parentColumn = "id", entityColumn = "slotId")
    val posts: List<PostEntity>,
)
