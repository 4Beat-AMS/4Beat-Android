package com.fourbeat.domain.usecase.music

import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.repository.MusicRepository
import javax.inject.Inject

class SearchSongsUseCase @Inject constructor(
    private val musicRepository: MusicRepository,
) {
    suspend operator fun invoke(query: String, limit: Int): Result<List<Song>> = runCatching {
        musicRepository.searchSongs(query = query, limit = limit)
    }
}
