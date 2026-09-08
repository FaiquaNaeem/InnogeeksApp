package com.example.innogeeks.feature_profile.presentation.profile

sealed interface ProfileAction {
    data class OnSectionToggled(val section: ProfileSection) : ProfileAction
    data object OnLoginClick : ProfileAction
    data object OnLogOutClick : ProfileAction
    data object OnLogOutConfirmed : ProfileAction
    data object OnLogOutDismissed : ProfileAction
    data object OnRetryClick : ProfileAction
    data object OnEditClick : ProfileAction
    data class OnFullNameChange(val value: String) : ProfileAction
    data class OnPhoneChange(val value: String) : ProfileAction
    data object OnSaveClick : ProfileAction
    data object OnCancelEditClick : ProfileAction
    data object OnDeleteAccountClick : ProfileAction
    data object OnDeleteAccountDismissed : ProfileAction
    data class OnDeleteConfirmationInputChange(val value: String) : ProfileAction
    data object OnDeleteAccountConfirmed : ProfileAction
}
