package com.dimasla4ee.playlistmaker.creator

import android.content.SharedPreferences
import com.dimasla4ee.playlistmaker.data.local.SearchHistorySharedPrefs
import com.dimasla4ee.playlistmaker.data.local.SearchHistoryStorage
import com.dimasla4ee.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.dimasla4ee.playlistmaker.data.repository.SettingsRepositoryImpl
import com.dimasla4ee.playlistmaker.data.local.SettingsSharedPrefs
import com.dimasla4ee.playlistmaker.data.local.SettingsStorage
import com.dimasla4ee.playlistmaker.data.repository.TrackSearchRepositoryImpl
import com.dimasla4ee.playlistmaker.domain.repository.SearchHistoryRepository
import com.dimasla4ee.playlistmaker.domain.repository.SettingsRepository
import com.dimasla4ee.playlistmaker.domain.repository.TrackSearchRepository
import com.dimasla4ee.playlistmaker.domain.use_case.SearchHistoryInteractor
import com.dimasla4ee.playlistmaker.domain.use_case.SearchTracksUseCase

object Creator {

    private lateinit var searchHistoryPrefs: SharedPreferences
    private lateinit var settingsSharedPrefs: SharedPreferences

    fun setSearchHistoryPrefs(prefs: SharedPreferences) {
        searchHistoryPrefs = prefs
    }

    fun setSettingsPrefs(prefs: SharedPreferences) {
        settingsSharedPrefs = prefs
    }

    private fun getTracksRepository(): TrackSearchRepository = TrackSearchRepositoryImpl()

    private fun getSearchHistoryRepository(): SearchHistoryRepository =
        SearchHistoryRepositoryImpl(getSearchHistoryStorage())

    private fun getSettingsRepository(): SettingsRepository =
        SettingsRepositoryImpl(getSettingsStorage())

    private fun getSearchHistoryStorage(): SearchHistoryStorage =
        SearchHistorySharedPrefs(searchHistoryPrefs)

    private fun getSettingsStorage(): SettingsStorage =
        SettingsSharedPrefs(settingsSharedPrefs)

    fun provideSearchTracksUseCase(): SearchTracksUseCase =
        SearchTracksUseCase(getTracksRepository())

    fun provideSearchHistoryInteractor(): SearchHistoryInteractor =
        SearchHistoryInteractor(getSearchHistoryRepository())

    fun provideSettingsInteractor(): SettingsInteractor =
        SettingsInteractor(getSettingsRepository())
}

class SettingsInteractor(
    private val settingsRepository: SettingsRepository
) {

    fun isDarkThemeEnabled() = settingsRepository.isDarkThemeEnabled()
    fun setAppTheme(useDarkTheme: Boolean) = settingsRepository.setAppTheme(useDarkTheme)
}