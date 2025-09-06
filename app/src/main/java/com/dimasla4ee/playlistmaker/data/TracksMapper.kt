package com.dimasla4ee.playlistmaker.data

import com.dimasla4ee.playlistmaker.data.dto.TrackDto
import com.dimasla4ee.playlistmaker.domain.models.Track
import com.dimasla4ee.playlistmaker.toMmSs

object TracksMapper {

    private const val ARTWORK_BIG_RESOLUTION_SUFFIX = "512x512bb.jpg"

    fun map(trackDto: TrackDto): Track = Track(
        id = trackDto.trackId,
        title = trackDto.trackName,
        artist = trackDto.artistName,
        country = trackDto.country,
        album = trackDto.collectionName,
        year = getYear(trackDto.releaseDate),
        genre = trackDto.primaryGenreName,
        duration = trackDto.trackTimeMillis.toMmSs(),
        thumbnailUrl = trackDto.artworkUrl100,
        coverUrl = getCover(trackDto.artworkUrl100),
        audioUrl = trackDto.previewUrl
    )

    private fun getYear(releaseDate: String): Int? = releaseDate.substring(0, 4).toIntOrNull()

    private fun getCover(thumbnailUrl: String): String =
        thumbnailUrl.replaceAfterLast('/', ARTWORK_BIG_RESOLUTION_SUFFIX)
}