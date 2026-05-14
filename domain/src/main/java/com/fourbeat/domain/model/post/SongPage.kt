package com.fourbeat.domain.model.post

data class SongPage(
    val songs: List<Song>,
    val nextUrl: String?,
)
