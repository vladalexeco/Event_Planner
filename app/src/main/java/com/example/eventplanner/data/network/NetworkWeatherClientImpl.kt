package com.example.eventplanner.data.network

import com.example.eventplanner.data.network.request.Request
import com.example.eventplanner.data.network.response.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkWeatherClientImpl(
    private val weatherApiService: WeatherApiService
) : NetworkWeatherClient {
    override suspend fun doRequest(dto: Request): Response {
        return withContext(Dispatchers.IO) {
            when(dto) {
                is Request.WeatherRequest -> getWeatherForecast(dto.location)
            }
        }
    }

    private suspend fun getWeatherForecast(location: String): Response {
        return try {
            weatherApiService.getWeather(location = location, days = 14)
        } catch (exception: Exception) {
            throw exception
        }
    }
}