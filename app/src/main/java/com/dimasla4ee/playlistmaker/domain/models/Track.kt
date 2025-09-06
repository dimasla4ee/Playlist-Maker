package com.dimasla4ee.playlistmaker.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val id: Int,
    val title: String,
    val artist: String,
    val country: String,
    val album: String?,
    val year: Int?,
    val genre: String,
    val duration: String,
    val thumbnailUrl: String,
    val coverUrl: String,
    val audioUrl: String
) : Parcelable