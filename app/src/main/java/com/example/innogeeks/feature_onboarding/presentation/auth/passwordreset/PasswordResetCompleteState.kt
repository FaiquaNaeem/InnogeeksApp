package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

import com.example.innogeeks.core.presentation.UiText

data class PasswordResetCompleteState(
    val collegeEmail: String = "",
    val passwordResetToken: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
    val formError: UiText? = null,
    val isSubmitting: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false
)
