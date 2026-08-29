package com.example.innogeeks.feature_events.presentation.di

import com.example.innogeeks.feature_events.presentation.events.EventsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val eventsPresentationModule = module {
    viewModelOf(::EventsViewModel)
}
