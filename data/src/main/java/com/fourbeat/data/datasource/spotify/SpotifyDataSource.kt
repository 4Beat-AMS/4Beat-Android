package com.fourbeat.data.datasource.spotify

import com.fourbeat.data.network.dto.music.SpotifyTrackResponse

interface SpotifyDataSource {
    suspend fun searchTracks(
        query: String,
        limit: Int = 5,
        offset: Int = 0,
    ): SpotifyTrackResponse
}
