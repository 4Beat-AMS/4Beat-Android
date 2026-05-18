package com.fourbeat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fourbeat.data.database.dao.PostDao
import com.fourbeat.data.database.entity.PostEntity
import com.fourbeat.data.database.entity.PostStatusConverter

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
@TypeConverters(PostStatusConverter::class)
abstract class FourBeatDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
