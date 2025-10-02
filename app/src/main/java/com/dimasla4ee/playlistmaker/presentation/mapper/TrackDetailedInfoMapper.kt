package com.dimasla4ee.playlistmaker.presentation.mapper

import com.dimasla4ee.playlistmaker.domain.model.Track
import com.dimasla4ee.playlistmaker.presentation.model.TrackDetailedInfo
import com.dimasla4ee.playlistmaker.presentation.util.toMmSs

object TrackDetailedInfoMapper {

    private const val ARTWORK_BIG_RESOLUTION_SUFFIX = "512x512bb.jpg"

    fun map(track: Track): TrackDetailedInfo = TrackDetailedInfo(
        title = track.title,
        artist = track.artist,
        country = track.country,
        album = track.album,
        year = track.releaseDate?.year,
        genre = track.genre,
        duration = track.duration.toMmSs(),
        coverUrl = getCover(track.thumbnailUrl),
        audioUrl = track.audioUrl
    )

    private fun getCover(thumbnailUrl: String): String = thumbnailUrl.replaceAfterLast(
        '/', ARTWORK_BIG_RESOLUTION_SUFFIX
    )
}