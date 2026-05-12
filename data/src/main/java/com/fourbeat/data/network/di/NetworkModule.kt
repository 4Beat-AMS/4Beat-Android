package com.fourbeat.data.network.di

import com.fourbeat.data.BuildConfig
import com.fourbeat.domain.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private val networkJson = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private fun createKtorClient(): HttpClient =
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(networkJson)
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("DEFAULT_NETWORK").i(message)
                    }
                }
                level = LogLevel.BODY
            }
        }

    @Provides
    @Singleton
    @DefaultNetwork
    fun provideDefaultHttpClient(
        preferenceRepository: PreferenceRepository,
    ): HttpClient =
        createKtorClient().config {
            defaultRequest {
                url(BuildConfig.BASE_URL)
                contentType(ContentType.Application.Json)
                runBlocking {
                    header("Authorization", preferenceRepository.uidFlow.first())
                }
            }
            /*install(Auth) {
                bearer {
                    loadTokens {
                        preferenceRepository
                            .uidFlow.first()?.let { uid ->
                                BearerTokens(uid.toString(), "")
                            }
                    }
                    sendWithoutRequest { request ->
                        val path = request.url.encodedPath
                        val shouldNotRequest = path.contains("login") || path.contains("register")
                        shouldNotRequest.not()
                    }
                }
            }*/
        }

    @Provides
    @Singleton
    @SpotifyNetwork
    fun provideSpotifyHttpClient(): HttpClient =
        createKtorClient().config {
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
}
