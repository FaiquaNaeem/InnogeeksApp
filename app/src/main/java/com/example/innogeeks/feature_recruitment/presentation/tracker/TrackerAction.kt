package com.example.innogeeks.feature_recruitment.presentation.tracker

sealed interface TrackerAction {
    data object OnRetryClick : TrackerAction
}
