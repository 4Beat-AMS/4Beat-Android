package com.fourbeat.domain.usecase.media

import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.repository.MediaRepository
import com.fourbeat.domain.usecase.music.SearchSongPageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMediaSongFlowUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val searchSongPageUseCase: SearchSongPageUseCase,
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
        val candidateList = searchSongPageUseCase(query = title, limit = 10)
            .getOrNull()?.songs ?: return null
        return candidateList.getOrNull(0)
    }
}