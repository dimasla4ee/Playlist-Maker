package com.dimasla4ee.playlistmaker.creator

import com.dimasla4ee.playlistmaker.data.TracksRepositoryImpl
import com.dimasla4ee.playlistmaker.domain.api.TracksRepository
import com.dimasla4ee.playlistmaker.domain.usecase.SearchTracksUseCase

object Creator {

    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl()
    }

    fun provideGetTracksUseCase(): SearchTracksUseCase {
        return SearchTracksUseCase(getTracksRepository())
    }
}