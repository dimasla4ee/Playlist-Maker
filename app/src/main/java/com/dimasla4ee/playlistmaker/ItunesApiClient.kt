package com.dimasla4ee.playlistmaker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun getSongs(
        query: String,
        doOnResponse: (call: Call<ItunesQueryResponse?>, response: Response<ItunesQueryResponse?>) -> Unit,
        doOnFailure: (call: Call<ItunesQueryResponse?>, t: Throwable) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            service.getSongs(query).enqueue(
                object : Callback<ItunesQueryResponse> {
                    override fun onResponse(
                        call: Call<ItunesQueryResponse?>,
                        response: Response<ItunesQueryResponse?>
                    ) {
                        doOnResponse(call, response)
                    }

                    override fun onFailure(
                        call: Call<ItunesQueryResponse?>,
                        t: Throwable
                    ) {
                        doOnFailure(call, t)
                    }

                }
            )
        }
    }

    interface ItunesService {
        @GET("/search")
        fun getSongs(
            @Query("term") query: String,
            @Query("media") mediaType: String = "music"
        ): Call<ItunesQueryResponse>
    }

    class ItunesQueryResponse(val resultCount: Int, val results: List<Track>)
}