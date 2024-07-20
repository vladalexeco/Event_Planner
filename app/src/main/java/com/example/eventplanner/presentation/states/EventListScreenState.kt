package com.example.eventplanner.presentation.states

import com.example.eventplanner.domain.models.Event

data class EventListScreenState(
    val eventList: List<Event> = emptyList(),
    val loadIndicator: Boolean = false
)
