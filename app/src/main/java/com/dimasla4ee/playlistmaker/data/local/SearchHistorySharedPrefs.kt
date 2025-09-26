package com.dimasla4ee.playlistmaker.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.dimasla4ee.playlistmaker.util.Keys

class SearchHistorySharedPrefs(
    val prefs: SharedPreferences
) : SearchHistoryStorage {

    override fun get(): String? = prefs.getString(Keys.Preference.SEARCH_HISTORY, null)

    override fun clear() {
        prefs.edit { remove(Keys.Preference.SEARCH_HISTORY) }
    }

    override fun save(tracksJson: String) {
        prefs.edit { putString(Keys.Preference.SEARCH_HISTORY, tracksJson) }
    }
}