package com.example.eventplanner.presentation.states

import com.example.eventplanner.domain.models.Event

sealed class EventEditEvent {
    data class saveEventToDatabase(val event: Event) : EventEditEvent()
}