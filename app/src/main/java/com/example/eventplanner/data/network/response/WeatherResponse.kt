package com.example.eventplanner.data.network.response

import com.example.eventplanner.data.network.models.CurrentWeatherDto
import com.example.eventplanner.data.network.models.ForecastDto

data class WeatherResponse(
    val current: CurrentWeatherDto,
    val forecast: ForecastDto
) : Response