package com.fourbeat.data.repository

import com.fourbeat.data.datasource.spotify.SpotifyDataSource
import com.fourbeat.data.mapper.toDomain
import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.repository.MusicRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepositoryImpl @Inject constructor(
    private val spotifyDataSource: SpotifyDataSource,
) : MusicRepository {
    private var accessToken: String? = null
    private var tokenExpiresAt: Long = 0L

    override suspend fun searchSongs(query: String, limit: Int): List<Song> {
        val token = getValidAccessToken()
        return spotifyDataSource.searchTracks(query = query, accessToken = token, limit = limit).toDomain()
    }

    private suspend fun getValidAccessToken(): String {
        if (accessToken != null && System.currentTimeMillis() < tokenExpiresAt) {
            return accessToken!!
        }
        val response = spotifyDataSource.getAccessToken()
        accessToken = response.accessToken
        tokenExpiresAt = System.currentTimeMillis() + response.expiresIn * 1_000L
        return response.accessToken
    }
}
