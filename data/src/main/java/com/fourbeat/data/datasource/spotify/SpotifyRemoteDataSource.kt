package com.fourbeat.data.datasource.spotify

import com.fourbeat.data.BuildConfig
import com.fourbeat.data.network.di.SpotifyNetwork
import com.fourbeat.data.network.dto.music.SpotifySearchResponse
import com.fourbeat.data.network.dto.music.SpotifyTokenResponse
import com.fourbeat.data.network.dto.music.SpotifyTrackResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.forms.submitForm
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyRemoteDataSource @Inject constructor(
    @param:SpotifyNetwork
    private val client: HttpClient,
) : SpotifyDataSource {
    private var accessToken: String? = null
    private var tokenExpiresAt: Long = 0L

    private suspend fun getValidAccessToken(): String {
        if (accessToken != null && System.currentTimeMillis() < tokenExpiresAt) {
            return accessToken!!
        }
        return fetchAndCacheToken()
    }

    private suspend fun fetchAndCacheToken(): String {
        val response = client.submitForm(
            url = TOKEN_URL,
            formParameters = parameters {
                append("grant_type", "client_credentials")
                append("client_id", BuildConfig.SPOTIFY_CLIENT_ID)
                append("client_secret", BuildConfig.SPOTIFY_CLIENT_SECRET)
            }
        ).body<SpotifyTokenResponse>()
        accessToken = response.accessToken
        tokenExpiresAt = System.currentTimeMillis() + response.expiresIn * 1_000L
        return response.accessToken
    }

    override suspend fun searchTracks(
        query: String,
        limit: Int,
        offset: Int,
    ): SpotifyTrackResponse {
        val token = getValidAccessToken()
        return try {
            fetchTracks(token, query, limit, offset)
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                fetchTracks(fetchAndCacheToken(), query, limit, offset)
            } else {
                throw e
            }
        }
    }

    private suspend fun fetchTracks(
        accessToken: String,
        query: String,
        limit: Int,
        offset: Int,
    ): SpotifyTrackResponse =
        client.get(SEARCH_URL) {
            header("Authorization", "Bearer $accessToken")
            parameter("q", query)
            parameter("type", "track")
            parameter("market", "KR")
            parameter("limit", limit)
            parameter("offset", offset)
        }.body<SpotifySearchResponse>().tracks

    companion object {
        private const val TOKEN_URL = "https://accounts.spotify.com/api/token"
        private const val SEARCH_URL = "https://api.spotify.com/v1/search"
    }
}
