package com.example.eventplanner.domain.usecases

import com.example.eventplanner.domain.api.EventStorageRepository
import com.example.eventplanner.domain.models.Event

class InsertEventToDatabaseUseCase(
    private val eventStorageRepository: EventStorageRepository
) {
    suspend operator fun invoke(event: Event) {
        eventStorageRepository.insertEventToDatabase(event)
    }
}