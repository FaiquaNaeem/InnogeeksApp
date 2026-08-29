package com.example.innogeeks.feature_events.presentation.events

sealed interface EventsAction {
    data class OnEventClick(val eventId: String) : EventsAction
    data object OnBackFromDetail : EventsAction
    data object OnRetry : EventsAction
}
