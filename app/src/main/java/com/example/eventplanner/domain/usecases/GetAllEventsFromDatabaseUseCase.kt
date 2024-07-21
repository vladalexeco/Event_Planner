package com.example.eventplanner.domain.usecases

import com.example.eventplanner.domain.api.EventStorageRepository
import com.example.eventplanner.domain.models.Event
import kotlinx.coroutines.flow.Flow

class GetAllEventsFromDatabaseUseCase(
    private val eventStorageRepository: EventStorageRepository
) {
    suspend operator fun invoke(): Flow<List<Event>> {
        return eventStorageRepository.getAllEventsFromDatabase()
    }
}