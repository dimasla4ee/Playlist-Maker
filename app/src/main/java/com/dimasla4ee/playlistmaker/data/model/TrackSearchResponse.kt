package com.dimasla4ee.playlistmaker.data.model

import com.dimasla4ee.playlistmaker.domain.model.Response

data class TrackSearchResponse(
    val results: List<TrackDto>
) : Response()