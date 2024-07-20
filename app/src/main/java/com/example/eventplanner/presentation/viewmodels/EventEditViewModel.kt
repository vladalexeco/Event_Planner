package com.example.eventplanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.domain.models.Event
import com.example.eventplanner.domain.usecases.DeleteEventFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.InsertEventToDatabaseUseCase
import com.example.eventplanner.presentation.states.EventEditEvent
import kotlinx.coroutines.launch

class EventEditViewModel(
    private val insertEventToDatabaseUseCase: InsertEventToDatabaseUseCase,
    private val deleteEventFromDatabaseUseCase: DeleteEventFromDatabaseUseCase
) : ViewModel() {

    fun onEvent(eventEditEvent: EventEditEvent) {
        when(eventEditEvent) {
            is EventEditEvent.saveEventToDatabase -> {
                saveEventToDatabase(eventEditEvent.event)
            }
        }
    }

    private fun saveEventToDatabase(event: Event) {
        viewModelScope.launch {
            insertEventToDatabaseUseCase(event)
        }
    }
}