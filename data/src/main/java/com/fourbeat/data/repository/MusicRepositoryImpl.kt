package com.fourbeat.data.repository

import com.fourbeat.data.datasource.spotify.SpotifyDataSource
import com.fourbeat.data.mapper.toDomain
import com.fourbeat.domain.model.post.SongPage
import com.fourbeat.domain.repository.MusicRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepositoryImpl @Inject constructor(
    private val spotifyDataSource: SpotifyDataSource,
) : MusicRepository {

    override suspend fun searchSongPage(query: String, limit: Int, nextUrl: String?): SongPage =
        spotifyDataSource.searchTracksPage(query = query, limit = limit, nextUrl = nextUrl).toDomain()
}
