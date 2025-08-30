package com.dimasla4ee.playlistmaker

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    fun getSongs(
        query: String,
        onSuccess: (call: Call<ItunesResponse?>, response: Response<ItunesResponse?>) -> Unit,
        onFailure: (call: Call<ItunesResponse?>, t: Throwable) -> Unit
    ) {
        service.getSongs(query).enqueue(object : Callback<ItunesResponse> {
            override fun onResponse(
                call: Call<ItunesResponse?>,
                response: Response<ItunesResponse?>
            ) {
                onSuccess(call, response)
            }

            override fun onFailure(
                call: Call<ItunesResponse?>,
                t: Throwable
            ) {
                onFailure(call, t)
            }

        })
    }

    private interface ItunesService {
        @GET("search")
        fun getSongs(
            @Query("term") query: String,
            @Query("media") mediaType: String = "music"
        ): Call<ItunesResponse>
    }

    data class ItunesResponse(
        val results: List<Track>
    )
}