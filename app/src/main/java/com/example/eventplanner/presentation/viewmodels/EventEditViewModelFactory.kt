package com.example.eventplanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.domain.usecases.DeleteEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.DeleteEventFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.InsertEventToDatabaseUseCase

class EventEditViewModelFactory(
    val insertEventToDatabaseUseCase: InsertEventToDatabaseUseCase,
    val deleteEventByIdFromDatabaseUseCase: DeleteEventByIdFromDatabaseUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EventEditViewModel(
            insertEventToDatabaseUseCase = insertEventToDatabaseUseCase,
            deleteEventByIdFromDatabaseUseCase = deleteEventByIdFromDatabaseUseCase
        ) as T
    }
}