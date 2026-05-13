package com.fourbeat.domain.repository

import com.fourbeat.domain.model.media.SongMeta
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getSongMetaFlow(): Flow<SongMeta?>
}