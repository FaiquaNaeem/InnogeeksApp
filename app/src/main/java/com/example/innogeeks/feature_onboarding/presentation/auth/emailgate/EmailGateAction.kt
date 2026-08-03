package com.example.innogeeks.feature_onboarding.presentation.auth.emailgate

sealed interface EmailGateAction {
    data class OnEmailChange(val email: String) : EmailGateAction
    data object OnContinueClick : EmailGateAction
    data object OnBackClick : EmailGateAction
}
