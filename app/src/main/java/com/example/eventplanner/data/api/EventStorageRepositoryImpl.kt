package com.example.eventplanner.data.api

import com.example.eventplanner.data.storage.AppDatabase
import com.example.eventplanner.data.storage.models.toEvent
import com.example.eventplanner.data.storage.models.toEventEntity
import com.example.eventplanner.domain.api.EventStorageRepository
import com.example.eventplanner.domain.models.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventStorageRepositoryImpl(
    private val appDatabase: AppDatabase
) : EventStorageRepository {
    override suspend fun insertEventToDatabase(event: Event) {
        appDatabase.getEventDao().insertEvent(event.toEventEntity())
    }

    override suspend fun deleteEventFromDatabase(event: Event) {
        appDatabase.getEventDao().deleteEvent(event.toEventEntity())
    }

    override suspend fun getAllEventsFromDatabase(): Flow<List<Event>> = flow {
        val eventsEntities = appDatabase.getEventDao().getAllEvents()
        emit(eventsEntities.map { eventEntity -> eventEntity.toEvent() })
    }

    override suspend fun getEventById(eventId: String): Event? {
        return appDatabase.getEventDao().getEventById(eventId)?.toEvent()
    }

    override suspend fun deleteEventById(eventId: String) {
        appDatabase.getEventDao().getEventById(eventId)
    }
}