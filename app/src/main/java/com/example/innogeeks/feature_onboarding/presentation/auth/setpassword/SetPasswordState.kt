package com.example.innogeeks.feature_onboarding.presentation.auth.setpassword

import com.example.innogeeks.core.presentation.UiText

data class SetPasswordState(
    val collegeEmail: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
    val formError: UiText? = null,
    val isSubmitting: Boolean = false
)
