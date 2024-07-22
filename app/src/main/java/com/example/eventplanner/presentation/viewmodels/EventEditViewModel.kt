package com.example.eventplanner.presentation.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.domain.models.Event
import com.example.eventplanner.domain.usecases.DeleteEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetForecastUseCase
import com.example.eventplanner.domain.usecases.InsertEventToDatabaseUseCase
import com.example.eventplanner.domain.util.Resource
import com.example.eventplanner.presentation.states.EventEditEvent
import com.example.eventplanner.presentation.states.EventEditScreenState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class EventEditViewModel(
    private val insertEventToDatabaseUseCase: InsertEventToDatabaseUseCase,
    private val deleteEventByIdFromDatabaseUseCase: DeleteEventByIdFromDatabaseUseCase,
    private val getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase,
    private val getForecastUseCase: GetForecastUseCase
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
            getForecastUseCase(location = "Москва", days = 0).collect {
                val weatherDataResource = it

                when(weatherDataResource) {
                    is Resource.Success -> {
                        Log.d("WEATHER", weatherDataResource.data.toString())
                    }
                    is Resource.Error -> {
                        Log.d("WEATHER", weatherDataResource.networkError.toString())
                    }
                }
            }
//            insertEventToDatabaseUseCase(event)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDaysBetween(event: Event): Int {
        val requiredDate = event.date
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val targetDate = LocalDate.parse(requiredDate, formatter)
        val currentDate = LocalDate.now()
        val daysBetween = ChronoUnit.DAYS.between(currentDate, targetDate).toInt()
        return daysBetween
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