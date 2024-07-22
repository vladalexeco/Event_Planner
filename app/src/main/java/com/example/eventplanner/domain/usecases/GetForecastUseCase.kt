package com.example.eventplanner.domain.usecases

import com.example.eventplanner.domain.api.ForecastRepository
import com.example.eventplanner.domain.models.WeatherData
import com.example.eventplanner.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetForecastUseCase(
    private val forecastRepository: ForecastRepository
) {
    suspend operator fun invoke(location: String, days: Int): Flow<Resource<WeatherData>> {
        return forecastRepository.getWeatherForecast(location = location, days = days)
    }
}