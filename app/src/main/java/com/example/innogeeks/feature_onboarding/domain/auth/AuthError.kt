package com.example.innogeeks.feature_onboarding.domain.auth

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Error

// Three distinct failure kinds, because each one is presented differently:
// Api -> a specific contract-defined message, Transport -> "check your connection",
// Validation -> an inline field error before any request is even sent.
sealed interface AuthError : Error {
    data class Api(val code: AuthApiError) : AuthError
    data class Transport(val error: DataError.Network) : AuthError
    data class Validation(val error: AuthValidationError) : AuthError
}
