package com.example.eventplanner.core.di

import android.content.Context
import androidx.room.Room
import com.example.eventplanner.data.api.EventStorageRepositoryImpl
import com.example.eventplanner.data.api.ForecastRepositoryImpl
import com.example.eventplanner.data.network.NetworkWeatherClient
import com.example.eventplanner.data.network.NetworkWeatherClientImpl
import com.example.eventplanner.data.network.WeatherApiService
import com.example.eventplanner.data.storage.AppDatabase
import com.example.eventplanner.domain.api.EventStorageRepository
import com.example.eventplanner.domain.api.ForecastRepository
import com.example.eventplanner.domain.util.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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


    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }


    @Provides
    fun provideNetworkWeatherClient(weatherApiService: WeatherApiService)
    : NetworkWeatherClient {
        return NetworkWeatherClientImpl(weatherApiService = weatherApiService)
    }


    @Provides
    fun provideForecastRepository(networkWeatherClient: NetworkWeatherClient)
    : ForecastRepository {
        return ForecastRepositoryImpl(networkWeatherClient = networkWeatherClient)
    }
}