package com.dimasla4ee.playlistmaker

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    @SerializedName("trackName") val title: String,
    @SerializedName("artistName") val artist: String,
    @SerializedName("trackTimeMillis") val durationInMillis: Long,
    @SerializedName("artworkUrl100") val artworkUrl: String
) {
    val formatedDuration: String
        get() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(durationInMillis)
}