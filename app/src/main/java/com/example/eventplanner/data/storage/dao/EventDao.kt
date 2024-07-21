package com.example.eventplanner.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eventplanner.data.storage.models.EventEntity

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(eventEntity: EventEntity)
    @Delete
    suspend fun deleteEvent(eventEntity: EventEntity)
    @Query("SELECT * FROM event_table")
    suspend fun getAllEvents(): List<EventEntity>
    @Query("SELECT * FROM event_table WHERE id = :id")
    suspend fun getEventById(id: String): EventEntity?
    @Query("DELETE FROM event_table WHERE id = :id")
    suspend fun deleteById(id: String)
}