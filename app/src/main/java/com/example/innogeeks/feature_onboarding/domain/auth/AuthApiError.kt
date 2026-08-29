package com.example.innogeeks.feature_onboarding.domain.auth

import com.example.innogeeks.core.domain.util.Error

// The contract's stable machine codes. UNSUPPORTED is required by §9: an unrecognised
// code means a server/app version mismatch and must never be coerced into a known case.
enum class AuthApiError : Error {
    VALIDATION_ERROR,
    APP_ACCESS_DENIED,
    PASSWORD_ALREADY_SET,
    VERIFICATION_CODE_COOLDOWN,
    VERIFICATION_CODE_INVALID,
    PASSWORD_SETUP_TOKEN_INVALID,
    PASSWORD_SETUP_NOT_ALLOWED,
    EMAIL_QUEUE_UNAVAILABLE,
    INVALID_CREDENTIALS,
    PASSWORD_NOT_SET,  // §10.1 - password reset called before first-login setup
    PASSWORD_RESET_COOLDOWN,  // §10.1 - resend cooldown for reset codes
    PASSWORD_RESET_CODE_INVALID,  // §10.2 - reset code wrong/expired/consumed
    PASSWORD_RESET_TOKEN_INVALID,  // §10.3 - reset token wrong/expired/used
    UNAUTHORIZED,  // §9 - token expired/invalid/missing
    UNSUPPORTED;

    companion object {
        fun fromCode(code: String): AuthApiError =
            entries.firstOrNull { it.name == code } ?: UNSUPPORTED
    }
}
