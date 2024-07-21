package com.example.eventplanner.domain.usecases

import com.example.eventplanner.domain.api.EventStorageRepository
import com.example.eventplanner.domain.models.Event

class GetEventByIdFromDatabaseUseCase(
    private val eventStorageRepository: EventStorageRepository
) {
    suspend operator fun invoke(eventId: String): Event? {
        return eventStorageRepository.getEventById(eventId)
    }
}