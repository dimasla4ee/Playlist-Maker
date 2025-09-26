package com.dimasla4ee.playlistmaker.data.repository

import com.dimasla4ee.playlistmaker.data.local.SearchHistoryStorage
import com.dimasla4ee.playlistmaker.data.local.LocalDateAdapter
import com.dimasla4ee.playlistmaker.domain.model.Track
import com.dimasla4ee.playlistmaker.domain.repository.SearchHistoryRepository
import com.dimasla4ee.playlistmaker.util.LogUtil
import com.google.gson.GsonBuilder
import java.time.LocalDate

class SearchHistoryRepositoryImpl(
    val storage: SearchHistoryStorage
) : SearchHistoryRepository {

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()
    private val cache: ArrayDeque<Track> = ArrayDeque()

    init {
        val savedTracks = storage.get()?.let {
            gson.fromJson(it, Array<Track>::class.java)
        } ?: emptyArray()

        cache.addAll(savedTracks)
    }

    override fun get(): List<Track> = cache.toList()

    override fun clear() = cache.clear()

    override fun add(newTrack: Track) {
        val duplicateTrackIndex = cache.indexOfFirst { it.id == newTrack.id }
        if (duplicateTrackIndex == 0) return

        val isNewTrackAlreadySaved = duplicateTrackIndex != -1

        cache.apply {
            when {
                isNewTrackAlreadySaved -> removeAt(duplicateTrackIndex)
                size >= SEARCH_HISTORY_LIMIT -> removeLast()
            }
            addFirst(newTrack)
        }
        LogUtil.d("SearchHistoryRepositoryImpl", "add: ${cache.joinToString(" ")}")
    }

    override fun save() {
        if (cache.isEmpty()) {
            storage.clear()
        } else {
            storage.save(gson.toJson(cache))
        }
    }

    private companion object {
        const val SEARCH_HISTORY_LIMIT = 10
    }
}

