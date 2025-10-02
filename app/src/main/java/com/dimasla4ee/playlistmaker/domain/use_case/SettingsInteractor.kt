package com.dimasla4ee.playlistmaker.domain.use_case

import com.dimasla4ee.playlistmaker.domain.repository.SettingsRepository

class SettingsInteractor(
    private val settingsRepository: SettingsRepository
) {

    fun isDarkThemeEnabled() = settingsRepository.isDarkThemeEnabled()
    fun setAppTheme(useDarkTheme: Boolean) = settingsRepository.setAppTheme(useDarkTheme)
}