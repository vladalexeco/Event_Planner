package com.example.eventplanner.data.network.models

import com.example.eventplanner.domain.models.submodels.Day
import com.google.gson.annotations.SerializedName

data class DayDto(
    @SerializedName("maxtemp_c")
    val maxTempC: Double,
    @SerializedName("mintemp_c")
    val minTempC: Double,
    @SerializedName("avgtemp_c")
    val avgTempC: Double,
    @SerializedName("maxwind_kph")
    val maxWindKph: Double,
    @SerializedName("avgvis_km")
    val avgVisKm: Double,
    @SerializedName("avghumidity")
    val avgHumidity: Int,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,
    val condition: ConditionDto,
    val uv: Int
)

fun DayDto.toDay(): Day {
    return Day(
        maxTempC = this.maxTempC,
        minTempC = this.minTempC,
        avgTempC = this.avgTempC,
        maxWindKph = this.maxWindKph,
        avgVisKm = this.avgVisKm,
        avgHumidity = this.avgHumidity,
        dailyChanceOfRain = this.dailyChanceOfRain,
        dailyChanceOfSnow = this.dailyChanceOfSnow,
        condition = this.condition.toCondition(),
        uv = this.uv
    )
}