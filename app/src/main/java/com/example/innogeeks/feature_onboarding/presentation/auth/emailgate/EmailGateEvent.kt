package com.example.innogeeks.feature_onboarding.presentation.auth.emailgate

// The gate's whole job is to pick which of the two paths the user takes next.
sealed interface EmailGateEvent {
    data class NavigateToVerifyCode(val collegeEmail: String) : EmailGateEvent
    data class NavigateToPasswordLogin(val collegeEmail: String) : EmailGateEvent
    data object NavigateBack : EmailGateEvent
}
