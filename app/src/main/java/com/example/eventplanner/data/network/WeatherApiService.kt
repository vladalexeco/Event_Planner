package com.example.eventplanner.data.network

import com.example.eventplanner.data.network.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    fun getWeather(
        @Query("key") apiKey: String = "90e44a406d754a719e9180637241907",
        @Query("q") location: String,
        @Query("days") days: Int
    ): WeatherResponse
}