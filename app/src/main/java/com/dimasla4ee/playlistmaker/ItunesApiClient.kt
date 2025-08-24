package com.dimasla4ee.playlistmaker

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ItunesApiClient {
    private const val BASE_URL = "https://itunes.apple.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val service: ItunesService by lazy {
        retrofit.create(ItunesService::class.java)
    }

    suspend fun getSongs(query: String): Result<List<Track>> = runCatching {
        service.getSongs(query).results
    }

    private interface ItunesService {
        @GET("search")
        suspend fun getSongs(
            @Query("term") query: String,
            @Query("media") mediaType: String = "music"
        ): ItunesQueryResponse
    }

    private data class ItunesQueryResponse(
        val results: List<Track>
    )
}