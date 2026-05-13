package com.fourbeat.domain.usecase.media

import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMediaSongFlowUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    operator fun invoke(): Flow<Song?> =
        mediaRepository
            .getSongMetaFlow()
            .map { songMeta ->
                songMeta?.let {
                    Song(
                        title = it.title,
                        artist = it.artist,
                        albumImageUrl = it.albumImageUrl ?: ""
                    )
                }
            }
}