package com.dimasla4ee.playlistmaker.domain.repository

interface SettingsRepository {

    fun isDarkThemeEnabled(): Boolean
    fun setAppTheme(useDarkTheme: Boolean)
}