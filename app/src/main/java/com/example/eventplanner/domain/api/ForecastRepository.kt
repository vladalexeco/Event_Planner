package com.example.eventplanner.domain.api

import com.example.eventplanner.domain.models.WeatherData
import com.example.eventplanner.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    suspend fun getWeatherForecast(location: String): Flow<Resource<WeatherData>>
}