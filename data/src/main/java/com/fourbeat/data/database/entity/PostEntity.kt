package com.fourbeat.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(
    tableName = "posts",
    foreignKeys = [ForeignKey(
        entity = SlotEntity::class,
        parentColumns = ["id"],
        childColumns = ["slotId"],
        onDelete = ForeignKey.CASCADE,
    )],
    indices = [Index(value = ["slotId"])],
)
data class PostEntity(
    @PrimaryKey val id: Long,
    val slotId: Long,
    val songTitle: String,
    val songArtist: String,
    val albumImageUrl: String?,
    val filePath: String?,
    val videoUrl: String?,
    val comment: String?,
    val createdAt: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).format(Date()),
    val status: PostStatus,
)
