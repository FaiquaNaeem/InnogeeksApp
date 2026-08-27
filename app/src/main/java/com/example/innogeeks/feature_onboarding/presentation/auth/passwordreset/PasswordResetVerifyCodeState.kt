package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

import com.example.innogeeks.core.presentation.UiText

data class PasswordResetVerifyCodeState(
    val collegeEmail: String = "",
    val code: String = "",
    val codeError: UiText? = null,
    val formError: UiText? = null,
    val isSubmitting: Boolean = false,
    val canResend: Boolean = true,
    val resendCountdown: Int = 0
)
