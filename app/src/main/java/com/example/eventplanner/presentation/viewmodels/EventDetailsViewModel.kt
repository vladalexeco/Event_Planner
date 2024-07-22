package com.example.eventplanner.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase
import com.example.eventplanner.presentation.states.EventDetailsEvent
import com.example.eventplanner.presentation.states.EventDetailsScreenState
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(EventDetailsScreenState())
    val uiState: LiveData<EventDetailsScreenState> = _uiState

    fun onEvent(eventDetailsEvent: EventDetailsEvent) {
        when(eventDetailsEvent) {
            is EventDetailsEvent.GetEventByIdEvent -> {
                getEventById(eventDetailsEvent.eventId)
            }
        }
    }

    private fun getEventById(eventId: String) {
        viewModelScope.launch {
            val event = getEventByIdFromDatabaseUseCase(eventId = eventId)
            if (event != null) {
                val eventDetailsScreenState = EventDetailsScreenState(
                    event = event
                )

                _uiState.value = eventDetailsScreenState
            }
        }
    }
}