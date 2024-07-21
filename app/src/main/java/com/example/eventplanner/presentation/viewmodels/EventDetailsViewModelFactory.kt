package com.example.eventplanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase

class EventDetailsViewModelFactory(
    val getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventDetailsViewModel(
            getEventByIdFromDatabaseUseCase = getEventByIdFromDatabaseUseCase
        ) as T
    }
}