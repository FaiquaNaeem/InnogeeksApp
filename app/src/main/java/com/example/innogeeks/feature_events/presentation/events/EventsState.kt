package com.example.innogeeks.feature_events.presentation.events

import com.example.innogeeks.feature_events.domain.model.ClubEvent

data class EventsState(
    val isLoading: Boolean = true,
    val events: List<ClubEvent> = emptyList(),
    val error: String? = null
) {
    // Soonest-future first, oldest-past last — one flat list, no upcoming/past split.
    val sortedEvents: List<ClubEvent>
        get() = events.sortedByDescending { it.date }
}
