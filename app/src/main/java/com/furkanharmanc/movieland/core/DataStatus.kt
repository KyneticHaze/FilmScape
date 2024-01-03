package com.furkanharmanc.movieland.core

sealed class DataStatus<out T> {
    data class Success<out T>(val data: T) : DataStatus<T>()
    data class Error(val errorMessage: String) : DataStatus<Nothing>()
    data object Loading : DataStatus<Nothing>()
}