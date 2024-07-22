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
                is Request.WeatherRequest -> {
                    getWeatherForecast(
                        location = dto.location,
                        days = dto.days
                    )
                }
            }
        }
    }

    private suspend fun getWeatherForecast(location: String, days: Int): Response {
        return try {
            weatherApiService.getWeather(location = location, days = days)
        } catch (exception: Exception) {
            throw exception
        }
    }
}