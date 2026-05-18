package com.fourbeat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fourbeat.data.database.dao.GroupFeedMetaDao
import com.fourbeat.data.database.dao.PostDao
import com.fourbeat.data.database.dao.SlotDao
import com.fourbeat.data.database.entity.GroupFeedMetaEntity
import com.fourbeat.data.database.entity.PostEntity
import com.fourbeat.data.database.entity.PostStatusConverter
import com.fourbeat.data.database.entity.SlotEntity

@Database(
    entities = [PostEntity::class, SlotEntity::class, GroupFeedMetaEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(PostStatusConverter::class)
abstract class FourBeatDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun slotDao(): SlotDao
    abstract fun groupFeedMetaDao(): GroupFeedMetaDao
}
