package com.fourbeat.data.database.entity

import androidx.room.TypeConverter

class PostStatusConverter {
    @TypeConverter
    fun fromStatus(status: PostStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): PostStatus = PostStatus.valueOf(value)
}
