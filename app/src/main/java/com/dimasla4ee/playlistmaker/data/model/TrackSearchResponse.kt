package com.dimasla4ee.playlistmaker.data.model

import com.dimasla4ee.playlistmaker.domain.model.Response
import kotlinx.serialization.Serializable

@Serializable
data class TrackSearchResponse(
    val results: List<TrackDto>
) : Response()