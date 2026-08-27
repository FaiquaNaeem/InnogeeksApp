package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

sealed interface PasswordResetVerifyCodeAction {
    data class OnCodeChange(val code: String) : PasswordResetVerifyCodeAction
    data object OnVerifyClick : PasswordResetVerifyCodeAction
    data object OnResendClick : PasswordResetVerifyCodeAction
    data object OnBackClick : PasswordResetVerifyCodeAction
}
