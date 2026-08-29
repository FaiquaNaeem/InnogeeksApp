package com.example.innogeeks.feature_events.presentation.events

import com.example.innogeeks.feature_events.domain.model.ClubEvent

enum class EventTab { UPCOMING, PAST }

data class EventsState(
    val isLoading: Boolean = true,
    val events: List<ClubEvent> = emptyList(),
    val selectedTab: EventTab = EventTab.UPCOMING,
    // Id of the event whose detail page is showing, or null for the list.
    val selectedEventId: String? = null,
    val error: String? = null
) {
    val visibleEvents: List<ClubEvent>
        get() = events.filter { it.isUpcoming == (selectedTab == EventTab.UPCOMING) }

    val selectedEvent: ClubEvent?
        get() = events.find { it.id == selectedEventId }
}
