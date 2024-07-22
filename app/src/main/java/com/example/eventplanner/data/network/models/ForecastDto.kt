package com.example.eventplanner.data.network.models

import com.example.eventplanner.domain.models.submodels.Forecast

data class ForecastDto(
    val forecastday: List<ForecastDayDto>
)

fun ForecastDto.toForecast(): Forecast {
    return Forecast(
        forecastDay = this.forecastday.map { forecastday -> forecastday.toForecastDay() }
    )
}