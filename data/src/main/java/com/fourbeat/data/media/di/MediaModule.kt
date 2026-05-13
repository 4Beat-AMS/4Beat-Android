package com.fourbeat.data.media.di

import android.content.Context
import android.media.session.MediaSessionManager
import com.fourbeat.data.datasource.media.MediaSessionDataSource
import com.fourbeat.data.datasource.media.MediaSessionLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MediaModule {
    @Provides
    @Singleton
    fun providesMediaSessionManager(
        @ApplicationContext context: Context
    ): MediaSessionManager =
        context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager

    @Provides
    fun providesMediaSessionDataSource(
        @ApplicationContext context: Context,
        sessionManager: MediaSessionManager,
    ): MediaSessionDataSource =
        MediaSessionLocalDataSource(context = context, sessionManager = sessionManager)
}