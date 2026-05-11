package com.fourbeat.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val uidFlow: Flow<Long?>
    suspend fun saveUid(uid: Long)
    suspend fun clearUid()
}
