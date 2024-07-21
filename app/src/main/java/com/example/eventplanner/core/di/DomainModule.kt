package com.example.eventplanner.core.di

import com.example.eventplanner.domain.api.EventStorageRepository
import com.example.eventplanner.domain.usecases.DeleteEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.DeleteEventFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetAllEventsFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.InsertEventToDatabaseUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideDeleteEventByIdFromDatabaseUseCase(
        eventStorageRepository: EventStorageRepository
    ): DeleteEventByIdFromDatabaseUseCase {
        return DeleteEventByIdFromDatabaseUseCase(eventStorageRepository)
    }

    @Provides
    fun provideDeleteEventFromDatabaseUseCase(
        eventStorageRepository: EventStorageRepository
    ): DeleteEventFromDatabaseUseCase {
        return DeleteEventFromDatabaseUseCase(eventStorageRepository)
    }

    @Provides
    fun provideGetAllEventsFromDatabaseUseCase(
        eventStorageRepository: EventStorageRepository
    ): GetAllEventsFromDatabaseUseCase {
        return GetAllEventsFromDatabaseUseCase(eventStorageRepository)
    }

    @Provides
    fun provideGetEventByIdFromDatabaseUseCase(
        eventStorageRepository: EventStorageRepository
    ): GetEventByIdFromDatabaseUseCase {
        return GetEventByIdFromDatabaseUseCase(eventStorageRepository)
    }

    @Provides
    fun provideInsertEventToDatabaseUseCase(
        eventStorageRepository: EventStorageRepository
    ): InsertEventToDatabaseUseCase {
        return InsertEventToDatabaseUseCase(eventStorageRepository)
    }
}