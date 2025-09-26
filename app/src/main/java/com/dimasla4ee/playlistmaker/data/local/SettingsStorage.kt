package com.dimasla4ee.playlistmaker.data.local

interface SettingsStorage {

    fun getDarkThemeState(): Boolean
    fun saveDarkThemeState(useDarkTheme: Boolean)
}