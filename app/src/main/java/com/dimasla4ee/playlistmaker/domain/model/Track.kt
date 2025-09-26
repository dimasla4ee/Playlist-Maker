package com.dimasla4ee.playlistmaker.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Track(
    val id: Int,
    val title: String,
    val artist: String,
    val country: String,
    val album: String?,
    val releaseDate: LocalDate?,
    val genre: String,
    val duration: Long,
    val thumbnailUrl: String,
    val audioUrl: String
) : Parcelable