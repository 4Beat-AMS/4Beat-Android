package com.fourbeat.data.datasource.media

import com.fourbeat.data.media.MediaMeta
import kotlinx.coroutines.flow.Flow

interface MediaSessionDataSource {
    fun getMediaMetaStream(): Flow<MediaMeta>
}