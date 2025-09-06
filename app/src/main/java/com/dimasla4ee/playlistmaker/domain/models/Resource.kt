package com.dimasla4ee.playlistmaker.domain.models

sealed interface Resource<T> {

    data class Success<T>(val data: T) : Resource<T>
    data class Failure<T>(val message: String) : Resource<T>
}