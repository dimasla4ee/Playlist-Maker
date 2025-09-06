package com.dimasla4ee.playlistmaker.domain.usecase

import com.dimasla4ee.playlistmaker.domain.api.TracksRepository
import com.dimasla4ee.playlistmaker.domain.models.Resource
import com.dimasla4ee.playlistmaker.domain.models.Track
import java.util.concurrent.Executors

class SearchTracksUseCase(
    private val tracksRepository: TracksRepository
) {
    private val executor = Executors.newSingleThreadExecutor()

    fun execute(query: String, consumer: Consumer<List<Track>>) {
        executor.execute {
            val tracksResource = tracksRepository.searchTracks(query)

            when (tracksResource) {
                is Resource.Failure -> {
                    consumer.consume(ConsumerData.Error(tracksResource.message))
                }

                is Resource.Success -> {
                    consumer.consume(ConsumerData.Data(tracksResource.data))
                }
            }

        }
    }
}