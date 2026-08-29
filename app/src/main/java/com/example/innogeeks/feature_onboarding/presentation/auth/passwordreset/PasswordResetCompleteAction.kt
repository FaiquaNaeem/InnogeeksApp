package com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset

sealed interface PasswordResetCompleteAction {
    data class OnPasswordChange(val password: String) : PasswordResetCompleteAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : PasswordResetCompleteAction
    data object OnTogglePasswordVisibility : PasswordResetCompleteAction
    data object OnToggleConfirmPasswordVisibility : PasswordResetCompleteAction
    data object OnResetPasswordClick : PasswordResetCompleteAction
}
