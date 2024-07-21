package com.example.eventplanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase

class EventDetailsViewModel(
    private val getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase
) : ViewModel() {


}