package com.example.innogeeks.feature_events.presentation.events

import kotlinx.serialization.Serializable

// Local nav graph scoped to the Events tab's content area.
@Serializable
internal data object EventListRoute

@Serializable
internal data class EventDetailRoute(val eventId: String)
