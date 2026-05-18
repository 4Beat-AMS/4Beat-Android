package com.fourbeat.presentation.ui.component

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.fourbeat.presentation.model.post.VideoSource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@OptIn(UnstableApi::class)
@EntryPoint
@InstallIn(SingletonComponent::class)
interface VideoCacheEntryPoint {
    fun cacheDataSourceFactory(): CacheDataSource.Factory
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    source: VideoSource,
    isActive: Boolean = true,
) {
    val context = LocalContext.current

    val cacheDataSourceFactory = remember {
        EntryPointAccessors.fromApplication(
            context.applicationContext,
            VideoCacheEntryPoint::class.java,
        ).cacheDataSourceFactory()
    }

    val uri = remember(source) {
        when (source) {
            is VideoSource.Local -> source.file.toUri()
            is VideoSource.Remote -> source.url.toUri()
        }
    }

    val exoPlayer = remember(uri) {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(uri))
                repeatMode = ExoPlayer.REPEAT_MODE_ONE
                prepare()
            }
    }

    LaunchedEffect(isActive, exoPlayer) {
        if (isActive) exoPlayer.play() else exoPlayer.pause()
    }

    DisposableEffect(exoPlayer) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            PlayerView(ctx).apply {
                useController = false
                controllerAutoShow = false
                setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
                setKeepContentOnPlayerReset(true)
                setShutterBackgroundColor(android.graphics.Color.TRANSPARENT)
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        },
        update = { view ->
            if (view.player != exoPlayer) {
                view.player = exoPlayer
            }
            view.useController = false
            view.hideController()
        },
    )
}
