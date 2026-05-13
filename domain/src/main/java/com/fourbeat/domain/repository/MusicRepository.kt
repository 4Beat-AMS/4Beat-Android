package com.fourbeat.domain.repository

import com.fourbeat.domain.model.post.Song

interface MusicRepository {
    suspend fun searchSongs(query: String, limit: Int): List<Song>
}
