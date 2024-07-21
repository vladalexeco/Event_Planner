package com.example.eventplanner.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.domain.usecases.DeleteEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetAllEventsFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase
import com.example.eventplanner.presentation.states.EventListEvent
import com.example.eventplanner.presentation.states.EventListScreenState
import kotlinx.coroutines.launch

class EventListViewModel(
    private val getAllEventsFromDatabaseUseCase: GetAllEventsFromDatabaseUseCase,
    private val getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase,
    private val deleteEventByIdFromDatabaseUseCase: DeleteEventByIdFromDatabaseUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData(EventListScreenState())
    val uiState: LiveData<EventListScreenState> = _uiState

    fun onEvent(eventListEvent: EventListEvent) {
        when(eventListEvent) {
            EventListEvent.GetAllEventsFromDatabase -> {
                getAllEventsFromDatabase()
            }
        }
    }

    private fun getAllEventsFromDatabase() {
        viewModelScope.launch {
            getAllEventsFromDatabaseUseCase().collect { events ->

                val newScreenState = _uiState.value?.copy(eventList = events)
                _uiState.value = newScreenState
            }
        }
    }
}