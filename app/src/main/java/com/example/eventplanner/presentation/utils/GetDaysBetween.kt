package com.example.eventplanner.presentation.utils

import com.example.eventplanner.domain.models.Event
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun getDaysBetween(eventDate: String): Int {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val targetDate = LocalDate.parse(eventDate, formatter)
    val currentDate = LocalDate.now()
    val daysBetween = ChronoUnit.DAYS.between(currentDate, targetDate).toInt() + 1
    return daysBetween
}