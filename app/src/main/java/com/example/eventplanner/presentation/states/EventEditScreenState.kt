package com.example.eventplanner.presentation.states

import com.example.eventplanner.domain.models.Event

data class EventEditScreenState(
    val event: Event? = null,
    val goToScreen: Boolean = false,
    val toastMessage: String? = null
)
