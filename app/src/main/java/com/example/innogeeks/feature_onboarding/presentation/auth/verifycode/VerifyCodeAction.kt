package com.example.innogeeks.feature_onboarding.presentation.auth.verifycode

sealed interface VerifyCodeAction {
    data class OnCodeChange(val code: String) : VerifyCodeAction
    data object OnVerifyClick : VerifyCodeAction
    data object OnResendClick : VerifyCodeAction
    data object OnBackClick : VerifyCodeAction
}
