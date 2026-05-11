package com.fourbeat.domain.repository

interface PreferenceRepository {
    suspend fun getUid(): Long?
    suspend fun saveUid(uid: Long)
    suspend fun clearUid()
}
