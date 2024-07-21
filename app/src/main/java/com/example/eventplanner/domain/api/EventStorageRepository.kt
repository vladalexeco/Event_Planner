package com.example.eventplanner.domain.api

import com.example.eventplanner.domain.models.Event
import kotlinx.coroutines.flow.Flow

interface EventStorageRepository {
    suspend fun insertEventToDatabase(event: Event)
    suspend fun deleteEventFromDatabase(event: Event)
    suspend fun getAllEventsFromDatabase(): Flow<List<Event>>
    suspend fun getEventById(eventId: String): Event?
    suspend fun deleteEventById(eventId: String)
}