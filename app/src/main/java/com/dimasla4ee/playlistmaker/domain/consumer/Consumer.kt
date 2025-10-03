package com.dimasla4ee.playlistmaker.domain.consumer

interface Consumer<T> {

    fun consume(data: ConsumerData<T>)
}