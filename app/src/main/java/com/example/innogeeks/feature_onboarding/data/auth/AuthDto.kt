package com.example.innogeeks.feature_onboarding.data.auth

import kotlinx.serialization.Serializable

// Request/response bodies exactly as docs/APP_API_CONTRACT.md §4-§8 define them.
// These sit inside the ApiEnvelope's "data" field, never at the top level.

@Serializable
data class EmailGateRequest(val collegeEmail: String)

@Serializable
data class EmailGateResponse(val nextStep: String)

@Serializable
data class VerificationCodeRequest(val collegeEmail: String)

@Serializable
data class VerificationCodeResponse(val requested: Boolean)

// code is a String so a leading zero survives — §2 is explicit about this.
@Serializable
data class VerifyCodeRequest(val collegeEmail: String, val code: String)

@Serializable
data class VerifyCodeResponse(val passwordSetupToken: String)

@Serializable
data class SetPasswordRequest(val passwordSetupToken: String, val password: String)

@Serializable
data class SetPasswordResponse(val accessToken: String)

@Serializable
data class LoginRequest(val collegeEmail: String, val password: String)

@Serializable
data class LoginResponse(val accessToken: String)

// Password reset flow (§10)
@Serializable
data class PasswordResetRequestRequest(val collegeEmail: String)

@Serializable
data class PasswordResetRequestResponse(val requested: Boolean)

@Serializable
data class PasswordResetVerifyRequest(val collegeEmail: String, val code: String)

@Serializable
data class PasswordResetVerifyResponse(val passwordResetToken: String)

@Serializable
data class PasswordResetCompleteRequest(val passwordResetToken: String, val password: String)

@Serializable
data class PasswordResetCompleteResponse(val accessToken: String)

// Logout (§11) - no request body, empty response
@Serializable
data class LogoutResponse(val success: Boolean = true)
