package com.example.eventplanner.domain.models

import com.example.eventplanner.domain.models.submodels.Forecast

data class WeatherData(
    val forecast: Forecast
)
