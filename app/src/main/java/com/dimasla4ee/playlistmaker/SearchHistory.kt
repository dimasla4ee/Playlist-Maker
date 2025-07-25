package com.dimasla4ee.playlistmaker

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

class SearchHistory(
    private val sharedPreferences: SharedPreferences
) {
    private lateinit var savedTracks: MutableList<Track>

    init {
        get()
    }

    fun get(): List<Track> {
        val json = sharedPreferences.getString(SEARCH_HISTORY_KEY, "")
        val array = Gson().fromJson(json, Array<Track>::class.java)
        savedTracks = array?.toMutableList() ?: mutableListOf()
        return savedTracks
    }

    fun removeAll() {
        if (savedTracks.isEmpty()) return

        savedTracks.clear()
        val json = Gson().toJson(savedTracks)
        sharedPreferences.edit {
            putString(SEARCH_HISTORY_KEY, json)
        }
    }

    fun add(newTrack: Track) {
        if (savedTracks.any { it.id == newTrack.id }) return

        savedTracks.apply {
            if (size < SEARCH_HISTORY_LIMIT) {
                add(newTrack)
            } else {
                removeAt(lastIndex)
                add(0, newTrack)
            }
        }

        val json = Gson().toJson(savedTracks)

        sharedPreferences.edit {
            putString(SEARCH_HISTORY_KEY, json)
        }
    }

    private companion object {
        const val SEARCH_HISTORY_LIMIT = 10
        const val SEARCH_HISTORY_KEY = "HISTORY_KEY"
    }
}