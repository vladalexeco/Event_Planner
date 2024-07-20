package com.example.eventplanner.data.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.eventplanner.domain.models.Event

@Entity(tableName = "event_table")
data class EventEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val date: String,
    val time: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val status: String,
    val district: String?,
    val forecast: String?,
    val forecastExtend: String?,
    val forecastImageUrl: String?
)
fun Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = this.id,
        name = this.name,
        date = this.date,
        time = this.time,
        latitude = this.latitude,
        longitude = this.longitude,
        description = this.description,
        status = this.status,
        district = this.district,
        forecast = this.forecast,
        forecastExtend = this.forecastExtend,
        forecastImageUrl = this.forecastImageUrl
    )
}

fun EventEntity.toEvent(): Event {
    return Event(
        id = this.id,
        name = this.name,
        date = this.date,
        time = this.time,
        latitude = this.latitude,
        longitude = this.longitude,
        description = this.description,
        status = this.status,
        district = this.district,
        forecast = this.forecast,
        forecastExtend = this.forecastExtend,
        forecastImageUrl = this.forecastImageUrl
    )
}
