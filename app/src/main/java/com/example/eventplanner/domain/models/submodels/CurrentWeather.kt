package com.example.eventplanner.domain.models.submodels

data class CurrentWeather(
    val lastUpdated: String,
    val tempC: Double,
    val condition: Condition,
    val windKph: Double,
    val windDir: String,
    val pressureMb: Double,
    val humidity: Int,
    val cloud: Int,
    val feelsLikeC: Double,
    val dewPointC: Double,
    val visKm: Int,
    val uv: Int,
)