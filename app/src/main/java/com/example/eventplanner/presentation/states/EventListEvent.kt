package com.example.eventplanner.presentation.states

sealed class EventListEvent {
    data object GetAllEventsFromDatabase: EventListEvent()
}