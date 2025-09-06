package com.dimasla4ee.playlistmaker.data

import com.dimasla4ee.playlistmaker.data.dto.TrackSearchRequest
import com.dimasla4ee.playlistmaker.data.dto.TrackSearchResponse
import com.dimasla4ee.playlistmaker.data.network.RetrofitNetworkClient
import com.dimasla4ee.playlistmaker.domain.api.TracksRepository
import com.dimasla4ee.playlistmaker.domain.models.Resource
import com.dimasla4ee.playlistmaker.domain.models.Track

class TracksRepositoryImpl : TracksRepository {

    override fun searchTracks(query: String): Resource<List<Track>> {
        val response = RetrofitNetworkClient.doRequest(TrackSearchRequest(query))
        return if (response.resultCode == 200) {
            Resource.Success(
                (response as TrackSearchResponse).results.map {
                    TracksMapper.map(it)
                }
            )
        } else {
            Resource.Failure("Something went wrong")
        }
    }
}