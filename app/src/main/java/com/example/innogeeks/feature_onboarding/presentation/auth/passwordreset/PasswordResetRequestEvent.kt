package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

sealed interface PasswordResetRequestEvent {
    data class NavigateToVerifyResetCode(val collegeEmail: String) : PasswordResetRequestEvent
    data object NavigateBack : PasswordResetRequestEvent
}
