package com.example.eventplanner.data.network.models

import com.example.eventplanner.domain.models.submodels.ForecastDay

data class ForecastDayDto(
    val date: String,
    val day: DayDto,
    val astro: AstroDto
)

fun ForecastDayDto.toForecastDay(): ForecastDay {
    return ForecastDay(
        date = this.date,
        day = this.day.toDay(),
        astro = this.astro.toAstro()
    )
}