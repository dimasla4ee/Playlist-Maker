package com.dimasla4ee.playlistmaker.creator

import android.content.SharedPreferences
import com.dimasla4ee.playlistmaker.data.local.SearchHistorySharedPrefs
import com.dimasla4ee.playlistmaker.data.local.SearchHistoryStorage
import com.dimasla4ee.playlistmaker.data.repository.SearchHistoryRepositoryImpl
import com.dimasla4ee.playlistmaker.data.repository.TrackSearchRepositoryImpl
import com.dimasla4ee.playlistmaker.domain.repository.SearchHistoryRepository
import com.dimasla4ee.playlistmaker.domain.repository.TrackSearchRepository
import com.dimasla4ee.playlistmaker.domain.use_case.SearchHistoryInteractor
import com.dimasla4ee.playlistmaker.domain.use_case.SearchTracksUseCase

object Creator {
    private lateinit var searchHistoryPrefs: SharedPreferences

    fun setSearchHistoryPrefs(prefs: SharedPreferences) {
        searchHistoryPrefs = prefs
    }

    private fun getTracksRepository(): TrackSearchRepository = TrackSearchRepositoryImpl()


    private fun getSearchHistoryRepository(): SearchHistoryRepository =
        SearchHistoryRepositoryImpl(getSearchHistoryStorage())


    private fun getSearchHistoryStorage(): SearchHistoryStorage =
        SearchHistorySharedPrefs(searchHistoryPrefs)


    fun provideSearchTracksUseCase(): SearchTracksUseCase =
        SearchTracksUseCase(getTracksRepository())


    fun provideSearchHistoryInteractor(): SearchHistoryInteractor =
        SearchHistoryInteractor(getSearchHistoryRepository())
}