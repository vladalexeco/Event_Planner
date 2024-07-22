package com.example.eventplanner.domain.models.submodels

data class ForecastDay(
    val date: String,
    val day: Day,
    val astro: Astro
)