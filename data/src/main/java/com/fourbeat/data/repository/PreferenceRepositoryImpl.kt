package com.fourbeat.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fourbeat.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : PreferenceRepository {
    override val uidFlow: Flow<Long?>
        get() = dataStore.data.map { it[UID] }

    override val nameFlow: Flow<String?>
        get() = dataStore.data.map { it[NAME] }

    override val nicknameFlow: Flow<String?>
        get() = dataStore.data.map { it[NICKNAME] }

    override suspend fun saveUid(uid: Long) {
        dataStore.edit { it[UID] = uid }
    }

    override suspend fun saveUserProfile(name: String, nickname: String) {
        dataStore.edit {
            it[NAME] = name
            it[NICKNAME] = nickname
        }
    }

    override suspend fun clearUid() {
        dataStore.edit { it.remove(UID) }
    }

    companion object {
        private val UID = longPreferencesKey("uid")
        private val NAME = stringPreferencesKey("name")
        private val NICKNAME = stringPreferencesKey("nickname")
    }
}
