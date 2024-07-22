package com.example.eventplanner.domain.usecases

import com.example.eventplanner.domain.api.EventStorageRepository

class DeleteEventByIdFromDatabaseUseCase(
    private val eventStorageRepository: EventStorageRepository
) {
    suspend operator fun invoke(eventId: String) {
        eventStorageRepository.deleteEventById(eventId)
    }
}