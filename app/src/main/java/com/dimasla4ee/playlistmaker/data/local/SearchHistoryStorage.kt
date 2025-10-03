package com.dimasla4ee.playlistmaker.data.local

interface SearchHistoryStorage {

    fun get(): String?
    fun clear()
    fun save(tracksJson: String)
}