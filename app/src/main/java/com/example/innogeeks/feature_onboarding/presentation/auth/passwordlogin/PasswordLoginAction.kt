package com.example.innogeeks.feature_onboarding.presentation.auth.passwordlogin

sealed interface PasswordLoginAction {
    data class OnPasswordChange(val password: String) : PasswordLoginAction
    data object OnTogglePasswordVisibility : PasswordLoginAction
    data object OnLoginClick : PasswordLoginAction
    data object OnBackClick : PasswordLoginAction
}
