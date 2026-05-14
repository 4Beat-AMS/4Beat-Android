package com.fourbeat.data.mapper

import com.fourbeat.data.network.dto.post.PostResponse
import com.fourbeat.data.network.dto.post.PostSongDto
import com.fourbeat.domain.model.post.Post
import com.fourbeat.domain.model.post.Song

fun PostResponse.toDomain(): Post =
    Post(
        id = id,
        author = author.toDomain(),
        song = song.toDomain(),
        videoUrl = videoUrl,
        comment = comment,
        createdAt = createdAt,
    )

fun PostSongDto.toDomain(): Song =
    Song(
        title = title,
        artist = artist,
        albumImageUrl = imageUrl,
    )
