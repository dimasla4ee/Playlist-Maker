package com.dimasla4ee.playlistmaker.domain.repository

import com.dimasla4ee.playlistmaker.domain.model.Resource
import com.dimasla4ee.playlistmaker.domain.model.Track

interface TrackSearchRepository {

    fun searchTracks(query: String): Resource<List<Track>>
}