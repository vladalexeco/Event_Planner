package com.example.eventplanner.presentation.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.domain.models.Event
import com.example.eventplanner.domain.models.WeatherData
import com.example.eventplanner.domain.usecases.DeleteEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetForecastUseCase
import com.example.eventplanner.domain.usecases.InsertEventToDatabaseUseCase
import com.example.eventplanner.domain.util.Resource
import com.example.eventplanner.presentation.states.EventEditEvent
import com.example.eventplanner.presentation.states.EventEditScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private fun handleLocation(event: Event): String {
        var location = event.location
        val separators = listOf(" ", ",", ";", ":")
        separators.forEach { separator ->
            if (location.contains(separator)) {
                location = location.split(separator)[0]
                return location
            }
        }
        return location
    }

    private fun deleteEventFromDatabase(eventId: String) {
        viewModelScope.launch {

            deleteEventByIdFromDatabaseUseCase(eventId)
        }
    }

    private fun getDaysBetween(event: Event): Int {
        val requiredDate = event.date
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val targetDate = LocalDate.parse(requiredDate, formatter)
        val currentDate = LocalDate.now()
        val daysBetween = ChronoUnit.DAYS.between(currentDate, targetDate).toInt() + 1
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

    private fun saveEventToDatabase(event: Event) {
        val dayBetween = getDaysBetween(event = event)
        val location = handleLocation(event = event)

        viewModelScope.launch {
            getForecastUseCase(location = location, days = dayBetween).collect { weatherDataResource ->
                when(weatherDataResource) {
                    is Resource.Success -> {
                        Log.d("WEATHER", weatherDataResource.data.toString())
                        val modifiedEvent = handleWithEvent(event, weatherDataResource.data)
                        withContext(Dispatchers.IO) {
                            insertEventToDatabaseUseCase(modifiedEvent)
                        }
                        _uiState.value = EventEditScreenState(goToScreen = true)
                    }
                    is Resource.Error -> {
                        Log.d("WEATHER", weatherDataResource.networkError.toString())
                        withContext(Dispatchers.IO) {
                            insertEventToDatabaseUseCase(event)
                        }
                        _uiState.value = EventEditScreenState(goToScreen = true)
                    }
                }
            }
        }
    }

    private fun handleWithEvent(event: Event, weatherData: WeatherData?): Event {
        val forecastList = weatherData?.forecast?.forecastDay
        val dateList = event.date.split(".")
        val compareDate = "${dateList[2]}-${dateList[1]}-${dateList[0]}"
        val forecastDay = forecastList?.find { it.date == compareDate }
        val eventForecast = "Temp ${forecastDay?.day?.avgTempC}"
        val eventForecastExtend = "Extend Temp ${forecastDay?.day?.avgTempC}"
        val imageUrl = forecastDay?.day?.condition?.icon
        val modifiedEvent = event.copy(
            forecast = eventForecast,
            forecastExtend = eventForecastExtend,
            forecastImageUrl = imageUrl
        )

        return modifiedEvent
    }
}