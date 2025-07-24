package com.dimasla4ee.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.edit

class App : Application() {

    var darkTheme = false
        private set
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean(THEME_KEY, darkTheme)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) MODE_NIGHT_YES else MODE_NIGHT_NO
        )
        sharedPreferences.edit {
            putBoolean(THEME_KEY, darkThemeEnabled)
        }
    }

    private companion object {
        const val PREF_KEY = "SHARED_PREF"
        const val THEME_KEY = "DARK_THEME"
    }
}