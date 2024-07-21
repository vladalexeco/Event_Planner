package com.example.eventplanner.presentation.states

import com.example.eventplanner.presentation.fragments.EventDetailsFragment

sealed class EventDetailsEvent {
    data class GetEventByIdEvent(val eventId: String) : EventDetailsEvent()
}