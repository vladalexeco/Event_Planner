package com.example.eventplanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.domain.usecases.DeleteEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetAllEventsFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase

class EventListViewModelFactory(
    val getAllEventsFromDatabaseUseCase: GetAllEventsFromDatabaseUseCase,
    val getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase,
    val deleteEventByIdFromDatabaseUseCase: DeleteEventByIdFromDatabaseUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventListViewModel(
            getAllEventsFromDatabaseUseCase = getAllEventsFromDatabaseUseCase,
            getEventByIdFromDatabaseUseCase = getEventByIdFromDatabaseUseCase,
            deleteEventByIdFromDatabaseUseCase = deleteEventByIdFromDatabaseUseCase
        ) as T
    }
}