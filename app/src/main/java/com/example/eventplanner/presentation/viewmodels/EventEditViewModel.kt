package com.example.eventplanner.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.domain.models.Event
import com.example.eventplanner.domain.usecases.DeleteEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.InsertEventToDatabaseUseCase
import com.example.eventplanner.presentation.states.EventEditEvent
import com.example.eventplanner.presentation.states.EventEditScreenState
import kotlinx.coroutines.launch

class EventEditViewModel(
    private val insertEventToDatabaseUseCase: InsertEventToDatabaseUseCase,
    private val deleteEventByIdFromDatabaseUseCase: DeleteEventByIdFromDatabaseUseCase,
    private val getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(EventEditScreenState())
    val uiState: LiveData<EventEditScreenState> = _uiState

    fun onEvent(eventEditEvent: EventEditEvent) {
        when(eventEditEvent) {
            is EventEditEvent.SaveEventToDatabase -> {
                saveEventToDatabase(eventEditEvent.event)
            }
            is EventEditEvent.GetEventByIdEvent -> {
                getEventFromDatabase(eventEditEvent.eventId)
            }

            is EventEditEvent.DeleteEventByIdFromDatabase -> {
                deleteEventFromDatabase(eventEditEvent.eventId)
            }
        }
    }

    private fun deleteEventFromDatabase(eventId: String) {
        viewModelScope.launch {

            deleteEventByIdFromDatabaseUseCase(eventId)
        }
    }

    private fun saveEventToDatabase(event: Event) {
        viewModelScope.launch {
            insertEventToDatabaseUseCase(event)
        }
    }

    private fun getEventFromDatabase(eventId: String) {
        viewModelScope.launch {
            val event = getEventByIdFromDatabaseUseCase(eventId)

            if (event != null) {
                val eventEditScreenState = EventEditScreenState(
                    event = event
                )

                _uiState.value = eventEditScreenState
            }
        }
    }
}