package com.dimasla4ee.playlistmaker.data.network

import com.dimasla4ee.playlistmaker.domain.model.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}