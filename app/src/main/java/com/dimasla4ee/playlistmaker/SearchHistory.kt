package com.dimasla4ee.playlistmaker

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

class SearchHistory(
    private val sharedPreferences: SharedPreferences
) {
    private val gson = Gson()
    private var savedTracks: ArrayDeque<Track> = ArrayDeque()

    init {
        val json = sharedPreferences.getString(PreferenceKeys.SEARCH_HISTORY, "")
        val array = gson.fromJson(json, Array<Track>::class.java) ?: emptyArray()
        savedTracks.addAll(array)
    }

    fun get(): List<Track> = savedTracks

    fun clear() {
        if (savedTracks.isEmpty()) return

        savedTracks.clear()

        saveSearchHistoryToPreferences()
    }

    fun add(newTrack: Track) {
        val duplicateTrackIndex = savedTracks.indexOfFirst { it.id == newTrack.id }
        val isNewTrackAlreadySaved = duplicateTrackIndex != -1
        val isMaxCapacityReached = savedTracks.size >= SEARCH_HISTORY_LIMIT

        if (duplicateTrackIndex == 0) return

        savedTracks.apply {
            when {
                isNewTrackAlreadySaved -> removeAt(duplicateTrackIndex)
                isMaxCapacityReached -> removeLast()
            }
            addFirst(newTrack)
        }

        saveSearchHistoryToPreferences()
    }

    private fun saveSearchHistoryToPreferences() {
        sharedPreferences.edit {
            val json = gson.toJson(savedTracks)
            putString(PreferenceKeys.SEARCH_HISTORY, json)
        }
    }

    private companion object {
        const val SEARCH_HISTORY_LIMIT = 10
    }
}