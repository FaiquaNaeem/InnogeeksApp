package com.example.innogeeks.feature_onboarding.presentation.auth.passwordlogin

import com.example.innogeeks.core.presentation.UiText

data class PasswordLoginState(
    val collegeEmail: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordError: UiText? = null,
    val formError: UiText? = null,
    val isSubmitting: Boolean = false
)
