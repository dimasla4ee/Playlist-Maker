package com.dimasla4ee.playlistmaker.data.model

data class TrackDto(
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    val country: String,
    val collectionName: String?,
    val releaseDate: String?,
    val primaryGenreName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val previewUrl: String?
)