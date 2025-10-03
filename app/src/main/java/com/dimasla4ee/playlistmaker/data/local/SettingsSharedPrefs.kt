package com.dimasla4ee.playlistmaker.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.dimasla4ee.playlistmaker.util.Keys

class SettingsSharedPrefs(
    private val prefs: SharedPreferences
) : SettingsStorage {

    override fun getDarkThemeState() = prefs.getBoolean(Keys.Preference.DARK_THEME, false)

    override fun saveDarkThemeState(useDarkTheme: Boolean) {
        prefs.edit { putBoolean(Keys.Preference.DARK_THEME, useDarkTheme) }
    }
}