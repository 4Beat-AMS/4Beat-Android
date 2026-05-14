package com.fourbeat.data.mapper

import com.fourbeat.data.media.MediaMeta
import com.fourbeat.domain.model.media.SongMeta

fun MediaMeta.toDomain(): SongMeta? {
    if (title.isNullOrBlank() || artist.isNullOrBlank()) return null

    val formattedImageUrl = when {
        albumArtUri == null -> null
        albumArtUri.startsWith("http") -> albumArtUri
        albumArtUri.contains("spotify") -> parseSpotifyImageUrl(albumArtUri)
        else -> null
    }
    return SongMeta(
        title = title,
        artist = artist,
        albumImageUrl = formattedImageUrl
    )
}

private fun parseSpotifyImageUrl(uri: String): String? {
    val delimiter = "image%3A"
    if (!uri.contains(delimiter)) return null

    return runCatching {
        val imageId = uri.substringAfter(delimiter).substringBefore("?")
        "https://i.scdn.co/image/$imageId"
    }.getOrNull()
}
