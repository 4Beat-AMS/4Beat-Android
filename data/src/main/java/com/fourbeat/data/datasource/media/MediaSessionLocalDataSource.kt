package com.fourbeat.data.datasource.media

import android.content.ComponentName
import android.content.Context
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import com.fourbeat.data.media.MediaMeta
import com.fourbeat.data.media.MediaNotificationListenerService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Singleton

@Singleton
class MediaSessionLocalDataSource(
    private val context: Context,
    private val sessionManager: MediaSessionManager,
) : MediaSessionDataSource {
    override fun getMediaMetaStream(): Flow<MediaMeta> = callbackFlow {
        val mediaCallback = object : MediaController.Callback() {
            override fun onMetadataChanged(metadata: MediaMetadata?) {
                metadata?.let { trySend(it.toMeta()) }
            }
        }
        val sessionListener = MediaSessionManager.OnActiveSessionsChangedListener { controllers ->
            val activeController = controllers?.find { controller ->
                controller.playbackState?.state == PlaybackState.STATE_PLAYING
            } ?: controllers?.firstOrNull()

            activeController?.let { controller ->
                controller.registerCallback(mediaCallback)
                controller.metadata?.let { trySend(it.toMeta()) }
            }
        }
        val componentName = ComponentName(context, MediaNotificationListenerService::class.java)
        try {
            sessionManager.addOnActiveSessionsChangedListener(sessionListener, componentName)
            val initialControllers = sessionManager.getActiveSessions(componentName)
            initialControllers.firstOrNull()?.let { controller ->
                controller.metadata?.let { trySend(it.toMeta()) }
            }
        } catch (e: SecurityException) {
            close(e)
        }

        awaitClose {
            sessionManager.removeOnActiveSessionsChangedListener(sessionListener)
        }
    }

    private fun MediaMetadata.toMeta(): MediaMeta =
        MediaMeta(
            title = getString(MediaMetadata.METADATA_KEY_TITLE),
            artist = getString(MediaMetadata.METADATA_KEY_ARTIST),
            albumArtUri = getString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI)
        )
}