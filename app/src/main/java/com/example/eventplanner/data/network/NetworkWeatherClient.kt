package com.example.eventplanner.data.network

import com.example.eventplanner.data.network.request.Request
import com.example.eventplanner.data.network.response.Response

interface NetworkWeatherClient {
    suspend fun doRequest(dto: Request): Response
}