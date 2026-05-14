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

    override suspend fun searchSongs(query: String, limit: Int): List<Song> =
        spotifyDataSource
            .searchTracks(query = query, limit = limit)
            .toDomain()
}
