package com.fourbeat.domain.usecase.media

import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.repository.MediaRepository
import com.fourbeat.domain.usecase.music.SearchSongsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMediaSongFlowUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val searchSongsUseCase: SearchSongsUseCase,
) {
    operator fun invoke(): Flow<Song?> =
        mediaRepository
            .getSongMetaFlow()
            .map { songMeta ->
                songMeta?.let {
                    Song(
                        title = it.title,
                        artist = it.artist,
                        albumImageUrl = it.albumImageUrl
                            ?: getFirstMatchedSong(it.title)?.albumImageUrl
                    )
                }
            }

    private suspend fun getFirstMatchedSong(title: String): Song? {
        val candidateList = searchSongsUseCase(query = title, limit = 10)
            .getOrNull() ?: return null
        return candidateList.getOrNull(0)
    }
}