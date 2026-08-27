package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

sealed interface PasswordResetRequestAction {
    data class OnEmailChange(val email: String) : PasswordResetRequestAction
    data object OnSendCodeClick : PasswordResetRequestAction
    data object OnBackClick : PasswordResetRequestAction
}
