package com.fourbeat.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val uidFlow: Flow<Long?>
    val nameFlow: Flow<String?>
    val nicknameFlow: Flow<String?>
    suspend fun saveUid(uid: Long)
    suspend fun saveUserProfile(name: String, nickname: String)
    suspend fun clearUid()
}
