package com.example.shelfify.data

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(val error: Throwable?) : Result<Nothing>()
}
