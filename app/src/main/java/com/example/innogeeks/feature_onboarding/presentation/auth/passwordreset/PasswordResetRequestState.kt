package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

import com.example.innogeeks.core.presentation.UiText

data class PasswordResetRequestState(
    val email: String = "",
    val emailError: UiText? = null,
    val formError: UiText? = null,
    val isSubmitting: Boolean = false
)
