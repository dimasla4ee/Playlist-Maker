package com.dimasla4ee.playlistmaker.domain.use_case

import com.dimasla4ee.playlistmaker.domain.consumer.Consumer
import com.dimasla4ee.playlistmaker.domain.consumer.ConsumerData
import com.dimasla4ee.playlistmaker.domain.model.Resource
import com.dimasla4ee.playlistmaker.domain.model.Track
import com.dimasla4ee.playlistmaker.domain.repository.TrackSearchRepository
import java.util.concurrent.Executors

class SearchTracksUseCase(
    private val trackSearchRepository: TrackSearchRepository
) {

    private val executor = Executors.newSingleThreadExecutor()

    fun execute(
        query: String,
        consumer: Consumer<List<Track>>
    ) {
        executor.execute {
            val tracksResource = trackSearchRepository.searchTracks(query)

            consumer.consume(
                when (tracksResource) {
                    is Resource.Failure -> {
                        ConsumerData.Error(tracksResource.message)
                    }

                    is Resource.Success -> {
                        ConsumerData.Data(tracksResource.data)
                    }
                }
            )
        }
    }
}