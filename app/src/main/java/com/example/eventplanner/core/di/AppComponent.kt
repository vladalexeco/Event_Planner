package com.example.eventplanner.core.di

import com.example.eventplanner.presentation.fragments.EventDetailsFragment
import com.example.eventplanner.presentation.fragments.EventEditFragment
import com.example.eventplanner.presentation.fragments.EventListFragment
import dagger.Component

@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(eventDetailsFragment: EventDetailsFragment)
    fun inject(eventEditFragment: EventEditFragment)
    fun inject(eventListFragment: EventListFragment)
}