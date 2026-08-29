package com.example.innogeeks.feature_events.presentation.events

import com.example.innogeeks.feature_events.domain.model.ClubEvent

data class EventsState(
    val isLoading: Boolean = true,
    val events: List<ClubEvent> = emptyList(),
    // Id of the event whose detail page is showing, or null for the list.
    val selectedEventId: String? = null,
    val error: String? = null
) {
    // Soonest-future first, oldest-past last — one flat list, no upcoming/past split.
    val sortedEvents: List<ClubEvent>
        get() = events.sortedByDescending { it.date }

    val selectedEvent: ClubEvent?
        get() = events.find { it.id == selectedEventId }
}
