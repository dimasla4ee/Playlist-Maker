package com.dimasla4ee.playlistmaker.data.dto

import com.dimasla4ee.playlistmaker.domain.models.Response

data class TrackSearchResponse(
    val results: List<TrackDto>
) : Response()