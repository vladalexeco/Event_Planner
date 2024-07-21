package com.example.eventplanner.data.network.request

sealed class Request {
    data class WeatherRequest(val location: String): Request()
}