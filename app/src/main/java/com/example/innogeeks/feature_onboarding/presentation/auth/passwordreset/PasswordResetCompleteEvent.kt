package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

sealed interface PasswordResetCompleteEvent {
    data object NavigateToHome : PasswordResetCompleteEvent
}
