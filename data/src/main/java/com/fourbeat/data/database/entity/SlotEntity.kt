package com.fourbeat.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "slots",
    indices = [
        Index(value = ["groupId", "date"]),
        Index(value = ["groupId", "date", "memberId"], unique = true),
    ],
)
data class SlotEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val groupId: Long,
    val date: String,
    val memberId: Long,
    val memberName: String,
    val memberNickname: String,
    val slotOrder: Int,
)
