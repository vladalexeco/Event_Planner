package com.example.eventplanner.domain.models.submodels

data class Day(
    val maxTempC: Double,
    val minTempC: Double,
    val avgTempC: Double,
    val maxWindKph: Double,
    val avgVisKm: Int,
    val avgHumidity: Int,
    val dailyChanceOfRain: Int,
    val dailyChanceOfSnow: Int,
    val condition: Condition,
    val uv: Int
)