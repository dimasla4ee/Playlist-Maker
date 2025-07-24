package com.dimasla4ee.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class App : Application() {

    var darkTheme = false

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE)
        darkTheme = sharedPreferences.getBoolean(THEME_KEY, darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                MODE_NIGHT_YES
            } else {
                MODE_NIGHT_NO
            }
        )
    }

    private companion object {
        const val PREF_KEY = "SHARED_PREF"
        const val THEME_KEY = "DARK_THEME"
    }
}