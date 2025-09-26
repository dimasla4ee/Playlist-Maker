package com.dimasla4ee.playlistmaker.domain.repository

import com.dimasla4ee.playlistmaker.domain.model.Track

interface SearchHistoryRepository {

    fun get(): List<Track>
    fun clear()
    fun save()
    fun add(newTrack: Track)
}