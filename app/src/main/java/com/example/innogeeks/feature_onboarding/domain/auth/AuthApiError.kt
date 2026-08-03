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
    UNSUPPORTED;

    companion object {
        fun fromCode(code: String): AuthApiError =
            entries.firstOrNull { it.name == code } ?: UNSUPPORTED
    }
}
