package com.example.eventplanner.domain.models

import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val date: String,
    val time: String,
    val description: String,
    val status: String,
    val location: String,
    val forecast: String? = null,
    val forecastExtend: String? = null,
    val forecastImageUrl: String? = null
)

