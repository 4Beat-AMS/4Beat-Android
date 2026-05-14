package com.fourbeat.data.mapper

import com.fourbeat.data.network.dto.music.SpotifyTrackItemResponse
import com.fourbeat.data.network.dto.music.SpotifyTrackResponse
import com.fourbeat.domain.model.post.Song

fun SpotifyTrackResponse.toDomain(): List<Song> =
    items.map { it.toDomain() }

fun SpotifyTrackItemResponse.toDomain(): Song =
    Song(
        title = name,
        artist = artists.firstOrNull()?.name ?: "Unknown Artist",
        albumImageUrl = album.images.firstOrNull()?.url ?: "", // TODO: 기본 이미지 url 추가
    )
