package com.fourbeat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.fourbeat.data.database.entity.SlotEntity
import com.fourbeat.data.database.entity.SlotWithPosts
import kotlinx.coroutines.flow.Flow

@Dao
interface SlotDao {

    @Transaction
    @Query("SELECT * FROM slots WHERE groupId = :groupId AND date = :date ORDER BY slotOrder ASC")
    fun observeByGroupAndDate(groupId: Long, date: String): Flow<List<SlotWithPosts>>

    @Insert
    suspend fun insert(slot: SlotEntity): Long

    @Update
    suspend fun update(slot: SlotEntity)

    @Query("SELECT * FROM slots WHERE groupId = :groupId AND date = :date AND memberId = :memberId LIMIT 1")
    suspend fun findByGroupDateMember(groupId: Long, date: String, memberId: Long): SlotEntity?

    @Query("DELETE FROM slots WHERE groupId = :groupId AND date = :date")
    suspend fun deleteByGroupAndDate(groupId: Long, date: String)

    @Transaction
    suspend fun upsert(slot: SlotEntity): Long {
        val existing = findByGroupDateMember(slot.groupId, slot.date, slot.memberId)
        return if (existing != null) {
            update(existing.copy(memberName = slot.memberName, memberNickname = slot.memberNickname, slotOrder = slot.slotOrder))
            existing.id
        } else {
            insert(slot.copy(id = 0))
        }
    }
}
