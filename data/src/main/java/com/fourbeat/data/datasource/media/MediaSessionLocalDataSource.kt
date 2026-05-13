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
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Singleton

@Singleton
class MediaSessionLocalDataSource(
    private val context: Context,
    private val sessionManager: MediaSessionManager,
) : MediaSessionDataSource {
    override fun getMediaMetaFlow(): Flow<MediaMeta> = callbackFlow {
        var currentController: MediaController? = null

        val mediaCallback = object : MediaController.Callback() {
            override fun onMetadataChanged(metadata: MediaMetadata?) {
                metadata?.let { trySend(it.toMeta()) }
            }
        }

        val sessionListener = MediaSessionManager.OnActiveSessionsChangedListener { controllers ->
            val activeController = controllers?.find {
                it.playbackState?.state == PlaybackState.STATE_PLAYING
            } ?: controllers?.firstOrNull()

            if (currentController?.packageName != activeController?.packageName) {
                currentController?.unregisterCallback(mediaCallback)
                currentController = activeController

                activeController?.let { controller ->
                    controller.registerCallback(mediaCallback)
                    controller.metadata?.let { trySend(it.toMeta()) }
                }
            }
        }

        val componentName = ComponentName(context, MediaNotificationListenerService::class.java)
        try {
            sessionManager.addOnActiveSessionsChangedListener(sessionListener, componentName)
            val initialController = sessionManager
                .getActiveSessions(componentName)
                .let { controllers ->
                    controllers.find { it.playbackState?.state == PlaybackState.STATE_PLAYING }
                        ?: controllers.firstOrNull()
                }
            currentController = initialController
            initialController?.let {
                it.registerCallback(mediaCallback)
                it.metadata?.let { meta -> trySend(meta.toMeta()) }
            }
        } catch (e: SecurityException) {
            close(e)
        }

        awaitClose {
            sessionManager.removeOnActiveSessionsChangedListener(sessionListener)
            currentController?.unregisterCallback(mediaCallback)
            currentController = null
        }
    }
        .distinctUntilChanged()

    private fun MediaMetadata.toMeta(): MediaMeta =
        MediaMeta(
            title = getString(MediaMetadata.METADATA_KEY_TITLE),
            artist = getString(MediaMetadata.METADATA_KEY_ARTIST),
            albumArtUri = getString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI)
        )
}