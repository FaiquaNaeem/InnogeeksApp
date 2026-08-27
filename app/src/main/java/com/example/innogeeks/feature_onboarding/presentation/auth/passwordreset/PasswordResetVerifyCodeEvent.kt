package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

sealed interface PasswordResetVerifyCodeEvent {
    data class NavigateToCompleteReset(
        val collegeEmail: String,
        val passwordResetToken: String
    ) : PasswordResetVerifyCodeEvent
    data object NavigateBack : PasswordResetVerifyCodeEvent
}
