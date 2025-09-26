package com.dimasla4ee.playlistmaker.data.repository

import com.dimasla4ee.playlistmaker.data.local.SettingsStorage
import com.dimasla4ee.playlistmaker.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val storage: SettingsStorage
) : SettingsRepository {

    override fun isDarkThemeEnabled(): Boolean = storage.getDarkThemeState()

    override fun setAppTheme(useDarkTheme: Boolean) {
        storage.saveDarkThemeState(useDarkTheme)
    }
}

