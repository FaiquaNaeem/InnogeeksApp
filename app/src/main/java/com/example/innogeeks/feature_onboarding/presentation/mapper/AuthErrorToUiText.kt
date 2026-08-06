package com.example.innogeeks.feature_onboarding.presentation.mapper

import com.example.innogeeks.R
import com.example.innogeeks.core.presentation.UiText
import com.example.innogeeks.core.presentation.mapper.toUiText
import com.example.innogeeks.feature_onboarding.domain.auth.AuthApiError
import com.example.innogeeks.feature_onboarding.domain.auth.AuthError

fun AuthError.toUiText(): UiText = when (this) {
    is AuthError.Api -> code.toUiText()
    is AuthError.Transport -> error.toUiText()
    is AuthError.Validation -> error.toUiText()
}

fun AuthApiError.toUiText(): UiText = when (this) {
    AuthApiError.VALIDATION_ERROR -> UiText.StringResource(R.string.error_validation)
    AuthApiError.APP_ACCESS_DENIED -> UiText.StringResource(R.string.error_app_access_denied)
    AuthApiError.PASSWORD_ALREADY_SET -> UiText.StringResource(R.string.error_password_already_set)
    AuthApiError.VERIFICATION_CODE_COOLDOWN -> UiText.StringResource(R.string.error_verification_code_cooldown)
    AuthApiError.VERIFICATION_CODE_INVALID -> UiText.StringResource(R.string.error_verification_code_invalid)
    AuthApiError.PASSWORD_SETUP_TOKEN_INVALID -> UiText.StringResource(R.string.error_password_setup_token_invalid)
    AuthApiError.PASSWORD_SETUP_NOT_ALLOWED -> UiText.StringResource(R.string.error_password_setup_not_allowed)
    AuthApiError.EMAIL_QUEUE_UNAVAILABLE -> UiText.StringResource(R.string.error_email_queue_unavailable)
    AuthApiError.INVALID_CREDENTIALS -> UiText.StringResource(R.string.error_invalid_credentials)
    AuthApiError.UNSUPPORTED -> UiText.StringResource(R.string.error_unsupported_version)
}
