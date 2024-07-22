package com.example.eventplanner.data.network.response

import com.example.eventplanner.data.network.models.ForecastDto

data class WeatherResponse(
    val forecast: ForecastDto
) : Response