package com.fourbeat.data.database.entity

import androidx.room.Entity

@Entity(tableName = "group_feed_meta", primaryKeys = ["groupId", "date"])
data class GroupFeedMetaEntity(
    val groupId: Long,
    val date: String,
    val nextDate: String?,
    val previousDate: String?,
)
