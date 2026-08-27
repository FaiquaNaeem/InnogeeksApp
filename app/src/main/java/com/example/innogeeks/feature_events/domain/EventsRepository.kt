package com.example.innogeeks.feature_events.domain

import com.example.innogeeks.feature_events.domain.model.ClubEvent

interface EventsRepository {
    suspend fun getEvents(): Result<List<ClubEvent>>
}
