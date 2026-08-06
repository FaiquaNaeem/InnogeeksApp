package com.example.innogeeks.feature_events.presentation.events

sealed interface EventsAction {
    data class OnTabSelected(val tab: EventTab) : EventsAction
    data class OnEventToggled(val eventId: String) : EventsAction
    data class OnRegisterClick(val eventId: String) : EventsAction
    data object OnRetry : EventsAction
}
