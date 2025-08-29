package com.dimasla4ee.playlistmaker

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Track(
    @SerializedName("trackId") val id: Int,
    @SerializedName("trackName") val title: String,
    @SerializedName("artistName") val artist: String,
    @SerializedName("country") val country: String,
    @SerializedName("collectionName") val album: String,
    @SerializedName("releaseDate") val releaseDate: String,
    @SerializedName("primaryGenreName") val genre: String,
    @SerializedName("trackTimeMillis") val durationInMillis: Long,
    @SerializedName("artworkUrl100") val urlPreviewArtwork: String,
    @SerializedName("previewUrl") val urlPreviewAudio: String
) {
    val year: Int?
        get() = releaseDate.substring(0, 4).toIntOrNull()

    val formatedDuration: String
        get() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(durationInMillis)

    val urlBigArtwork: String
        get() = urlPreviewArtwork.replaceAfterLast('/', ARTWORK_BIG_RESOLUTION_SUFFIX)

    private companion object {
        const val ARTWORK_BIG_RESOLUTION_SUFFIX = "512x512bb.jpg"
    }
}