package com.example.eventplanner.data.network.models

import com.example.eventplanner.domain.models.submodels.CurrentWeather

data class CurrentWeatherDto(
    val last_updated: String,
    val temp_c: Double,
    val condition: ConditionDto,
    val wind_kph: Double,
    val wind_dir: String,
    val pressure_mb: Double,
    val humidity: Int,
    val cloud: Int,
    val feelslike_c: Double,
    val dewpoint_c: Double,
    val vis_km: Int,
    val uv: Int,
)

fun CurrentWeatherDto.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        lastUpdated = this.last_updated,
        tempC = this.temp_c,
        condition = this.condition.toCondition(),
        windKph = this.wind_kph,
        windDir = this.wind_dir,
        pressureMb = this.pressure_mb,
        humidity = this.humidity,
        cloud = this.cloud,
        feelsLikeC = this.feelslike_c,
        dewPointC = dewpoint_c,
        visKm = this.vis_km,
        uv = this.uv
    )
}