package com.example.eventplanner.core.di

import android.content.Context
import com.example.eventplanner.domain.usecases.DeleteEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.DeleteEventFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetAllEventsFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.GetEventByIdFromDatabaseUseCase
import com.example.eventplanner.domain.usecases.InsertEventToDatabaseUseCase
import com.example.eventplanner.presentation.viewmodels.EventDetailsViewModelFactory
import com.example.eventplanner.presentation.viewmodels.EventEditViewModelFactory
import com.example.eventplanner.presentation.viewmodels.EventListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideEventEditViewModelFactory(
        insertEventToDatabaseUseCase: InsertEventToDatabaseUseCase,
        deleteEventByIdFromDatabaseUseCase: DeleteEventByIdFromDatabaseUseCase,
        getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase
    ): EventEditViewModelFactory {
        return EventEditViewModelFactory(
            insertEventToDatabaseUseCase = insertEventToDatabaseUseCase,
            deleteEventByIdFromDatabaseUseCase = deleteEventByIdFromDatabaseUseCase,
            getEventByIdFromDatabaseUseCase = getEventByIdFromDatabaseUseCase
        )
    }

    @Provides
    fun provideEventListViewModelFactory(
        getAllEventsFromDatabaseUseCase: GetAllEventsFromDatabaseUseCase,
        getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase,
        deleteEventByIdFromDatabaseUseCase: DeleteEventByIdFromDatabaseUseCase
    ): EventListViewModelFactory {
        return EventListViewModelFactory(
            getAllEventsFromDatabaseUseCase = getAllEventsFromDatabaseUseCase,
            getEventByIdFromDatabaseUseCase = getEventByIdFromDatabaseUseCase,
            deleteEventByIdFromDatabaseUseCase = deleteEventByIdFromDatabaseUseCase
        )
    }

    @Provides
    fun provideEventDetailsViewModelFactory(
        getEventByIdFromDatabaseUseCase: GetEventByIdFromDatabaseUseCase
    ) : EventDetailsViewModelFactory {
        return EventDetailsViewModelFactory(
            getEventByIdFromDatabaseUseCase = getEventByIdFromDatabaseUseCase
        )
    }
}