package com.fourbeat.data.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "posts",
    indices = [Index(value = ["groupId", "date"])],
)
data class PostEntity(
    @PrimaryKey val id: Long,
    val groupId: Long,
    val date: String,
    val memberId: Long,
    val memberName: String,
    val memberNickname: String,
    val slotOrder: Int,
    val songTitle: String,
    val songArtist: String,
    val albumImageUrl: String?,
    val filePath: String?,
    val videoUrl: String?,
    val comment: String?,
    val createdAt: String,
    val status: PostStatus,
    val workId: String?,
    val nextDate: String?,
    val previousDate: String?,
)
