package com.fourbeat.data.mapper

import com.fourbeat.data.network.dto.music.SpotifyTrackItemResponse
import com.fourbeat.data.network.dto.music.SpotifyTrackResponse
import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.model.post.SongPage

fun SpotifyTrackResponse.toSongPage(): SongPage =
    SongPage(
        songs = items.map { it.toDomain() },
        nextUrl = next,
    )

fun SpotifyTrackItemResponse.toDomain(): Song =
    Song(
        title = name,
        artist = artists.firstOrNull()?.name ?: "Unknown Artist",
        albumImageUrl = album.images.firstOrNull()?.url
    )
