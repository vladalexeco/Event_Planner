package com.example.eventplanner.core.di

import android.content.Context
import androidx.room.Room
import com.example.eventplanner.data.api.EventStorageRepositoryImpl
import com.example.eventplanner.data.storage.AppDatabase
import com.example.eventplanner.domain.api.EventStorageRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "event_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    fun provideEventStorageRepository(appDatabase: AppDatabase)
    : EventStorageRepository {
        return EventStorageRepositoryImpl(appDatabase)
    }
}