package com.example.innogeeks.feature_events.presentation.events

sealed interface EventsAction {
    data object OnRetry : EventsAction
}
