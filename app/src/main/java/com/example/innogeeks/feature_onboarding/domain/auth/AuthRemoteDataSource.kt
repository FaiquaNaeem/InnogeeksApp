package com.example.innogeeks.feature_onboarding.domain.auth

import com.example.innogeeks.core.domain.util.Result

// What email-gate tells the app to do next. Unsupported covers §9's version-mismatch rule.
enum class NextStep { PASSWORD_SETUP, PASSWORD_LOGIN, UNSUPPORTED }

// One function per contract endpoint. Errors are AuthError so error.code survives up to the UI.
interface AuthRemoteDataSource {

    suspend fun checkEmail(collegeEmail: String): Result<NextStep, AuthError>

    suspend fun requestVerificationCode(collegeEmail: String): Result<Unit, AuthError>

    // Returns the single-use, 10-minute password-setup token.
    suspend fun verifyCode(collegeEmail: String, code: String): Result<String, AuthError>

    // Both of these return an access token, which is what makes a user Registered.
    suspend fun setPassword(passwordSetupToken: String, password: String): Result<String, AuthError>

    suspend fun login(collegeEmail: String, password: String): Result<String, AuthError>
}
