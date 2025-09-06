package com.dimasla4ee.playlistmaker.domain.usecase

sealed interface ConsumerData<T> {

    data class Data<T>(val value: T) : ConsumerData<T>
    data class Error<T>(val message: String) : ConsumerData<T>
}