package com.fourbeat.domain.repository

import com.fourbeat.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val uidFlow: Flow<Long?>
    suspend fun getUser(): User
    suspend fun saveUser(user: User)
    suspend fun clearUser()
}
