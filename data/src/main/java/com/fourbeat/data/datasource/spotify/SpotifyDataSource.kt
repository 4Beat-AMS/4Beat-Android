package com.fourbeat.data.datasource.spotify

import com.fourbeat.data.network.dto.music.SpotifyTrackResponse

interface SpotifyDataSource {
    suspend fun searchTracksPage(
        query: String,
        limit: Int,
        nextUrl: String? = null,
    ): SpotifyTrackResponse
}
