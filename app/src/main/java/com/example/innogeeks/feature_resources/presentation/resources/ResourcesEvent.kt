package com.example.innogeeks.feature_resources.presentation.resources

sealed interface ResourcesEvent {
    data class OpenUrl(val url: String) : ResourcesEvent
}
