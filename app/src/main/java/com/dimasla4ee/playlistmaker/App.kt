package com.dimasla4ee.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.edit
import com.dimasla4ee.playlistmaker.creator.Creator
import com.dimasla4ee.playlistmaker.util.Keys

/*
TODO: разбить на data, presentation, domain слои
 */

class App : Application() {

    var isDarkThemeEnabled = false
        private set
    private lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        Creator.setSearchHistoryPrefs(getSharedPreferences(Keys.SEARCH_PREFERENCES, MODE_PRIVATE))
        prefs = getSharedPreferences(Keys.APP_PREFERENCES, MODE_PRIVATE)
        isDarkThemeEnabled = prefs.getBoolean(Keys.Preference.DARK_THEME, isDarkThemeEnabled)
        setAppTheme(isDarkThemeEnabled)
    }

    fun setAppTheme(useDarkTheme: Boolean) {
        isDarkThemeEnabled = useDarkTheme

        AppCompatDelegate.setDefaultNightMode(
            if (useDarkTheme) MODE_NIGHT_YES else MODE_NIGHT_NO
        )

        prefs.edit {
            putBoolean(Keys.Preference.DARK_THEME, useDarkTheme)
        }
    }
}