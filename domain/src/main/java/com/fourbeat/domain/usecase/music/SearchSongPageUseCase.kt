package com.fourbeat.domain.usecase.music

import com.fourbeat.domain.model.post.SongPage
import com.fourbeat.domain.repository.MusicRepository
import javax.inject.Inject

class SearchSongPageUseCase @Inject constructor(
    private val musicRepository: MusicRepository,
) {
    suspend operator fun invoke(
        query: String,
        limit: Int,
        nextUrl: String? = null,
    ): Result<SongPage> = runCatching {
        musicRepository.searchSongPage(query = query, limit = limit, nextUrl = nextUrl)
    }
}
