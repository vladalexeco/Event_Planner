package com.example.eventplanner.data.network.models

import com.example.eventplanner.domain.models.submodels.Day

data class DayDto(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val avgtemp_c: Double,
    val maxwind_kph: Double,
    val avgvis_km: Double,
    val avghumidity: Int,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val condition: ConditionDto,
    val uv: Int
)

fun DayDto.toDay(): Day {
    return Day(
        maxTempC = this.maxtemp_c,
        minTempC = this.mintemp_c,
        avgTempC = this.avgtemp_c,
        maxWindKph = this.maxwind_kph,
        avgVisKm = this.avgvis_km,
        avgHumidity = this.avghumidity,
        dailyChanceOfRain = this.daily_chance_of_rain,
        dailyChanceOfSnow = this.daily_chance_of_snow,
        condition = this.condition.toCondition(),
        uv = this.uv
    )
}