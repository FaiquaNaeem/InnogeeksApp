package com.example.innogeeks.feature_profile.presentation.profile

sealed interface ProfileEvent {
    data object NavigateToAuth : ProfileEvent
}
