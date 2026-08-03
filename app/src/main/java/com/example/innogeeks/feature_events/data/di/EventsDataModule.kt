package com.example.innogeeks.feature_events.data.di

import com.example.innogeeks.feature_events.data.InMemoryEventsRepository
import com.example.innogeeks.feature_events.domain.EventsRepository
import org.koin.dsl.module

val eventsDataModule = module {
    single<EventsRepository> { InMemoryEventsRepository() }
}
