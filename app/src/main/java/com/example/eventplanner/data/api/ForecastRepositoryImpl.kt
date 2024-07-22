package com.example.eventplanner.data.api

import com.example.eventplanner.data.network.NetworkWeatherClient
import com.example.eventplanner.data.network.models.toCurrentWeather
import com.example.eventplanner.data.network.models.toForecast
import com.example.eventplanner.data.network.request.Request
import com.example.eventplanner.data.network.response.WeatherResponse
import com.example.eventplanner.domain.api.ForecastRepository
import com.example.eventplanner.domain.models.WeatherData
import com.example.eventplanner.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ForecastRepositoryImpl(
    private val networkWeatherClient: NetworkWeatherClient
) : ForecastRepository {
    override suspend fun getWeatherForecast(location: String, days: Int): Flow<Resource<WeatherData>> = flow {
        try {
            val response = networkWeatherClient.doRequest(Request.WeatherRequest(location = location, days = days))
            val weatherData = WeatherData(
                forecast = (response as WeatherResponse).forecast.toForecast()
            )
            emit(Resource.Success(data = weatherData))
        } catch (exception: Exception) {
            val errorMessage = if (exception.message.isNullOrEmpty()) "Unknown Error" else
                exception.message
            emit(Resource.Error(networkError = errorMessage))
        }
    }
}