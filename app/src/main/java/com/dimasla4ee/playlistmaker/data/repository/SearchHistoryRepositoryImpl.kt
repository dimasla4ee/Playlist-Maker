package com.dimasla4ee.playlistmaker.data.repository

import com.dimasla4ee.playlistmaker.data.local.LocalDateSerializer
import com.dimasla4ee.playlistmaker.data.local.SearchHistoryStorage
import com.dimasla4ee.playlistmaker.domain.model.Track
import com.dimasla4ee.playlistmaker.domain.repository.SearchHistoryRepository
import com.dimasla4ee.playlistmaker.util.LogUtil
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDate

class SearchHistoryRepositoryImpl(
    val storage: SearchHistoryStorage
) : SearchHistoryRepository {

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        serializersModule = SerializersModule {
            contextual(LocalDate::class, LocalDateSerializer)
        }
    }
    private val cache: ArrayDeque<Track> = ArrayDeque()

    init {
        val savedTracks = storage.get()?.let { trackJson ->
            json.decodeFromString<List<Track>>(trackJson)
        } ?: emptyList()

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
            val cacheJson = json.encodeToString(cache.toList())
            storage.save(cacheJson)
        }
    }

    private companion object {
        const val SEARCH_HISTORY_LIMIT = 10
    }
}

