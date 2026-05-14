package com.fourbeat.data.repository

import com.fourbeat.data.datasource.media.MediaSessionDataSource
import com.fourbeat.data.datasource.media.NotificationListenerPermissionDataSource
import com.fourbeat.data.mapper.toDomain
import com.fourbeat.data.media.MediaMeta
import com.fourbeat.domain.model.media.SongMeta
import com.fourbeat.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepositoryImpl @Inject constructor(
    private val mediaSessionDataSource: MediaSessionDataSource,
    private val notificationListenerPermissionDataSource: NotificationListenerPermissionDataSource,
) : MediaRepository {
    override fun getSongMetaFlow(): Flow<SongMeta?> =
        mediaSessionDataSource
            .getMediaMetaFlow()
            .map(MediaMeta::toDomain)

    override fun getLiveSongPermissionFlow(): Flow<Boolean> =
        notificationListenerPermissionDataSource.getPermissionFlow()
}
