package com.fourbeat.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fourbeat.domain.model.user.User
import com.fourbeat.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : PreferenceRepository {

    override val uidFlow: Flow<Long?>
        get() = dataStore.data.map { it[UID] }

    override suspend fun getUser(): User {
        val prefs = dataStore.data.first()
        return User(
            id = prefs[UID] ?: error("uid not found"),
            name = prefs[NAME] ?: error("name not found"),
            nickname = prefs[NICKNAME] ?: error("nickname not found"),
        )
    }

    override suspend fun saveUser(user: User) {
        dataStore.edit {
            it[UID] = user.id
            it[NAME] = user.name
            it[NICKNAME] = user.nickname
        }
    }

    override suspend fun clearUser() {
        dataStore.edit { it.clear() }
    }

    companion object {
        private val UID = longPreferencesKey("uid")
        private val NAME = stringPreferencesKey("name")
        private val NICKNAME = stringPreferencesKey("nickname")
    }
}
