package com.fourbeat.data.datasource.spotify

import com.fourbeat.data.network.dto.music.SpotifyTokenResponse
import com.fourbeat.data.network.dto.music.SpotifyTrackResponse

interface SpotifyDataSource {
    suspend fun getAccessToken(): SpotifyTokenResponse
    suspend fun searchTracks(
        accessToken: String,
        query: String,
        limit: Int = 5,
        offset: Int = 0,
    ): SpotifyTrackResponse
}
