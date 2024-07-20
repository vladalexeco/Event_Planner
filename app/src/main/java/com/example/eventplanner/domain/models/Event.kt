package com.example.eventplanner.domain.models

import com.example.eventplanner.domain.util.UNSIGNED_EVENT_ID
import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val date: String,
    val time: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val status: String,
    val district: String? = null,
    val forecast: String? = null,
    val forecastExtend: String? = null,
    val forecastImageUrl: String? = null
)

