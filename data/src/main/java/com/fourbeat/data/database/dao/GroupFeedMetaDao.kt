package com.fourbeat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fourbeat.data.database.entity.GroupFeedMetaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupFeedMetaDao {

    @Query("SELECT * FROM group_feed_meta WHERE groupId = :groupId AND date = :date")
    fun observe(groupId: Long, date: String): Flow<GroupFeedMetaEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meta: GroupFeedMetaEntity)

    @Query("DELETE FROM group_feed_meta WHERE groupId = :groupId AND date = :date")
    suspend fun delete(groupId: Long, date: String)
}
