package com.dimasla4ee.playlistmaker.domain.use_case

import com.dimasla4ee.playlistmaker.domain.model.Track
import com.dimasla4ee.playlistmaker.domain.repository.SearchHistoryRepository

class SearchHistoryInteractor(
    private val searchHistoryRepository: SearchHistoryRepository
) {

    fun getSearchHistory() = searchHistoryRepository.get()
    fun addTrackToSearchHistory(track: Track) = searchHistoryRepository.add(track)
    fun clearSearchHistory() = searchHistoryRepository.clear()
    fun saveSearchHistory() = searchHistoryRepository.save()
}