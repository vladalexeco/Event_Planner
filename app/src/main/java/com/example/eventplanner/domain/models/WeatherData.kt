package com.example.eventplanner.domain.models

import com.example.eventplanner.domain.models.submodels.CurrentWeather
import com.example.eventplanner.domain.models.submodels.Forecast

data class WeatherData(
    val currentWeather: CurrentWeather,
    val forecast: Forecast
)
