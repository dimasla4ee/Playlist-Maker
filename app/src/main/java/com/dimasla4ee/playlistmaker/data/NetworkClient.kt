package com.dimasla4ee.playlistmaker.data

import com.dimasla4ee.playlistmaker.domain.models.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}