package com.dimasla4ee.playlistmaker.data.mapper

import com.dimasla4ee.playlistmaker.data.model.TrackDto
import com.dimasla4ee.playlistmaker.domain.model.Track
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TrackMapper {

    fun map(trackDto: TrackDto): Track? {
        val id = trackDto.trackId ?: return null
        val title = trackDto.trackName ?: return null
        val artist = trackDto.artistName ?: return null
        val country = trackDto.country ?: return null
        val genre = trackDto.primaryGenreName ?: return null
        val duration = trackDto.trackTimeMillis ?: return null
        val thumbnailUrl = trackDto.artworkUrl100 ?: return null
        val audioUrl = trackDto.previewUrl ?: return null

        return Track(
            id = id,
            title = title,
            artist = artist,
            country = country,
            album = trackDto.collectionName,
            releaseDate = trackDto.releaseDate?.toLocalDate(),
            genre = genre,
            duration = duration,
            thumbnailUrl = thumbnailUrl,
            audioUrl = audioUrl
        )
    }

    private fun String.toLocalDate(): LocalDate? = ZonedDateTime.parse(
        this, DateTimeFormatter.ISO_DATE_TIME
    ).toLocalDate()
}

