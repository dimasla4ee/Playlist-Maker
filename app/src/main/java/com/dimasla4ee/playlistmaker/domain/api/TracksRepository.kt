package com.dimasla4ee.playlistmaker.domain.api

import com.dimasla4ee.playlistmaker.domain.models.Resource
import com.dimasla4ee.playlistmaker.domain.models.Track

interface TracksRepository {
    fun searchTracks(query: String): Resource<List<Track>>
}