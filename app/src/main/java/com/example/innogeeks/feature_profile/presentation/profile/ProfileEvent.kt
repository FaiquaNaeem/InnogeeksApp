package com.example.innogeeks.feature_profile.presentation.profile

sealed interface ProfileEvent {
    data class ShowToast(val message: String) : ProfileEvent
}
