package com.example.innogeeks.feature_onboarding.presentation.auth.setpassword

sealed interface SetPasswordAction {
    data class OnPasswordChange(val password: String) : SetPasswordAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : SetPasswordAction
    data object OnTogglePasswordVisibility : SetPasswordAction
    data object OnSubmitClick : SetPasswordAction
}
