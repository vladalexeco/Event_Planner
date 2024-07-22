package com.example.eventplanner.presentation.states

import com.example.eventplanner.domain.models.Event

sealed class EventEditEvent {
    data class SaveEventToDatabase(val event: Event) : EventEditEvent()
    data class GetEventByIdEvent(val eventId: String) : EventEditEvent()
    data class DeleteEventByIdFromDatabase(val eventId: String) : EventEditEvent()
}