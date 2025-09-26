package com.dimasla4ee.playlistmaker.data.network

import com.dimasla4ee.playlistmaker.data.model.TrackSearchRequest
import com.dimasla4ee.playlistmaker.domain.model.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitNetworkClient : NetworkClient {

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

    override fun doRequest(dto: Any): Response = when (dto) {
        is TrackSearchRequest -> {
            val response = runCatching { service.getSongs(dto.term).execute() }.getOrNull()
            val body = response?.body() ?: Response()

            body.apply { resultCode = response?.code() ?: 0 }
        }

        else -> {
            Response().apply { resultCode = 400 }
        }
    }
}