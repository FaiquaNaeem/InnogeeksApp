package com.example.innogeeks.feature_events.presentation.events

import com.example.innogeeks.feature_events.domain.model.ClubEvent

enum class EventTab { UPCOMING, PAST }

data class EventsState(
    val isLoading: Boolean = true,
    val events: List<ClubEvent> = emptyList(),
    val selectedTab: EventTab = EventTab.UPCOMING,
    val expandedEventId: String? = null,
    // Local-only until a registration endpoint exists.
    val registeredEventIds: Set<String> = emptySet(),
    val error: String? = null
) {
    val visibleEvents: List<ClubEvent>
        get() = events.filter { it.isUpcoming == (selectedTab == EventTab.UPCOMING) }
}
