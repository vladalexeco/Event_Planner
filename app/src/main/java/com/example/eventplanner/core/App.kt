package com.example.eventplanner.core

import android.app.Application
import com.example.eventplanner.core.di.AppComponent
import com.example.eventplanner.core.di.AppModule
import com.example.eventplanner.core.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}