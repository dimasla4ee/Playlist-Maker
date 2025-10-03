package com.dimasla4ee.playlistmaker.data.repository

import com.dimasla4ee.playlistmaker.data.mapper.TrackMapper
import com.dimasla4ee.playlistmaker.data.model.TrackSearchRequest
import com.dimasla4ee.playlistmaker.data.model.TrackSearchResponse
import com.dimasla4ee.playlistmaker.data.network.RetrofitNetworkClient
import com.dimasla4ee.playlistmaker.domain.model.Resource
import com.dimasla4ee.playlistmaker.domain.model.Track
import com.dimasla4ee.playlistmaker.domain.repository.TrackSearchRepository

class TrackSearchRepositoryImpl : TrackSearchRepository {

    override fun searchTracks(
        query: String
    ): Resource<List<Track>> {
        val response = RetrofitNetworkClient.doRequest(TrackSearchRequest(query))

        return when (response.resultCode) {
            0 -> {
                Resource.Failure("No internet connection")
            }

            200 -> {
                Resource.Success(
                    (response as TrackSearchResponse).results.mapNotNull { trackDto ->
                        TrackMapper.map(trackDto)
                    }
                )
            }

            else -> {
                Resource.Failure("Something went wrong")
            }
        }
    }
}