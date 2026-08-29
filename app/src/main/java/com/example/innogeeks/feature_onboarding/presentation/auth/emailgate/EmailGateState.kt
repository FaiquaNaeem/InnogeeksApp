package com.example.innogeeks.feature_onboarding.presentation.auth.emailgate

import com.example.innogeeks.core.presentation.UiText

data class EmailGateState(
    val email: String = "",
    val emailError: UiText? = null,
    val formError: UiText? = null,
    val isSubmitting: Boolean = false
)
