package com.example.eventplanner.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eventplanner.data.storage.dao.EventDao
import com.example.eventplanner.data.storage.models.EventEntity

@Database(
    version = 1,
    entities = [EventEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getEventDao(): EventDao
}