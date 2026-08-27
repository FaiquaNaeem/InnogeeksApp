package com.example.innogeeks.feature_onboarding.presentation.auth.passwordlogin

sealed interface PasswordLoginEvent {
    data object NavigateToHome : PasswordLoginEvent
    data object NavigateToPasswordReset : PasswordLoginEvent
    data object NavigateBack : PasswordLoginEvent
}
