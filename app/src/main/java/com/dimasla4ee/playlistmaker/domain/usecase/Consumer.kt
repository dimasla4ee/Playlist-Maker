package com.dimasla4ee.playlistmaker.domain.usecase

interface Consumer<T> {

    fun consume(data: ConsumerData<T>)
}