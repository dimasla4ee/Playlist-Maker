package com.dimasla4ee.playlistmaker.data.mapper

import com.dimasla4ee.playlistmaker.data.model.TrackDto
import com.dimasla4ee.playlistmaker.domain.model.Track
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TrackMapper {

    fun map(trackDto: TrackDto): Track? = trackDto.previewUrl?.let { audioUrl ->
        Track(
            id = trackDto.trackId,
            title = trackDto.trackName,
            artist = trackDto.artistName,
            country = trackDto.country,
            album = trackDto.collectionName,
            releaseDate = trackDto.releaseDate?.toLocalDate(),
            genre = trackDto.primaryGenreName,
            duration = trackDto.trackTimeMillis,
            thumbnailUrl = trackDto.artworkUrl100,
            audioUrl = audioUrl
        )
    }

    private fun String.toLocalDate(): LocalDate? = ZonedDateTime.parse(
        this, DateTimeFormatter.ISO_DATE_TIME
    ).toLocalDate()
}

