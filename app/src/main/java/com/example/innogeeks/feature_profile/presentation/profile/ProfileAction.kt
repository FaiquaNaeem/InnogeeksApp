package com.example.innogeeks.feature_profile.presentation.profile

sealed interface ProfileAction {
    data class OnSectionToggled(val section: ProfileSection) : ProfileAction
    data object OnEditClick : ProfileAction
    data object OnLogOutClick : ProfileAction
}
