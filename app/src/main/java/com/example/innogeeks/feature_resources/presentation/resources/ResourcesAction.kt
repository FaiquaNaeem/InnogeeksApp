package com.example.innogeeks.feature_resources.presentation.resources

sealed interface ResourcesAction {
    data class OnResourceItemClicked(val url: String) : ResourcesAction
    data object OnRetry : ResourcesAction
}
