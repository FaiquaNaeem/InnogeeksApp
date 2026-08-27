package com.example.innogeeks.feature_onboarding.presentation.auth.setpassword

sealed interface SetPasswordEvent {
    // The token is already stored by this point, so the user is fully registered.
    data object NavigateToHome : SetPasswordEvent

    // An expired or spent setup token means the whole flow has to start over.
    data object RestartVerification : SetPasswordEvent
}
