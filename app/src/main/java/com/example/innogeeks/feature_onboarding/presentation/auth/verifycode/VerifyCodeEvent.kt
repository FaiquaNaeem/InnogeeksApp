package com.example.innogeeks.feature_onboarding.presentation.auth.verifycode

sealed interface VerifyCodeEvent {
    data class NavigateToSetPassword(
        val collegeEmail: String,
        val passwordSetupToken: String
    ) : VerifyCodeEvent

    // A 409 PASSWORD_ALREADY_SET means this is not a first login after all.
    data class NavigateToPasswordLogin(val collegeEmail: String) : VerifyCodeEvent

    data object NavigateBack : VerifyCodeEvent
}
