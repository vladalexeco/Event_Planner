package com.example.eventplanner.domain.util

sealed class Resource<T>(val data: T? = null, val networkError: String? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(networkError: String?, data: T? = null): Resource<T>(data, networkError)
}