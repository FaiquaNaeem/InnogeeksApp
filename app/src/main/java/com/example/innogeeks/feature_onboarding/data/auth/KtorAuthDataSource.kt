package com.example.innogeeks.feature_onboarding.data.auth

import com.example.innogeeks.core.data.networking.postEnveloped
import com.example.innogeeks.core.domain.error.ApiFailure
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.core.domain.util.asEmptyResult
import com.example.innogeeks.core.domain.util.mapData
import com.example.innogeeks.core.domain.util.mapError
import com.example.innogeeks.feature_onboarding.domain.auth.AuthApiError
import com.example.innogeeks.feature_onboarding.domain.auth.AuthError
import com.example.innogeeks.feature_onboarding.domain.auth.AuthRemoteDataSource
import com.example.innogeeks.feature_onboarding.domain.auth.NextStep
import io.ktor.client.HttpClient

// The real contract implementation. Written now but NOT bound in Koin — no host exists yet.
// Swapping it in is one line in OnboardingDataModule.kt.
class KtorAuthDataSource(private val httpClient: HttpClient) : AuthRemoteDataSource {

    override suspend fun checkEmail(collegeEmail: String): Result<NextStep, AuthError> =
        httpClient.postEnveloped<EmailGateRequest, EmailGateResponse>(
            route = "/auth/email-gate",
            body = EmailGateRequest(collegeEmail = collegeEmail)
        ).mapData { it.toNextStep() }.mapError { it.toAuthError() }

    override suspend fun requestVerificationCode(collegeEmail: String): Result<Unit, AuthError> =
        httpClient.postEnveloped<VerificationCodeRequest, VerificationCodeResponse>(
            route = "/auth/verification-code",
            body = VerificationCodeRequest(collegeEmail = collegeEmail)
        ).asEmptyResult().mapError { it.toAuthError() }

    override suspend fun verifyCode(
        collegeEmail: String,
        code: String
    ): Result<String, AuthError> =
        httpClient.postEnveloped<VerifyCodeRequest, VerifyCodeResponse>(
            route = "/auth/verify-code",
            body = VerifyCodeRequest(collegeEmail = collegeEmail, code = code)
        ).mapData { it.passwordSetupToken }.mapError { it.toAuthError() }

    override suspend fun setPassword(
        passwordSetupToken: String,
        password: String
    ): Result<String, AuthError> =
        httpClient.postEnveloped<SetPasswordRequest, SetPasswordResponse>(
            route = "/auth/set-password",
            body = SetPasswordRequest(passwordSetupToken = passwordSetupToken, password = password)
        ).mapData { it.accessToken }.mapError { it.toAuthError() }

    override suspend fun login(
        collegeEmail: String,
        password: String
    ): Result<String, AuthError> =
        httpClient.postEnveloped<LoginRequest, LoginResponse>(
            route = "/auth/login",
            body = LoginRequest(collegeEmail = collegeEmail, password = password)
        ).mapData { it.accessToken }.mapError { it.toAuthError() }
}

// An unrecognised nextStep is a version mismatch, not a default — §9.
private fun EmailGateResponse.toNextStep(): NextStep =
    NextStep.entries.firstOrNull { it.name == nextStep } ?: NextStep.UNSUPPORTED

private fun ApiFailure.toAuthError(): AuthError = when (this) {
    is ApiFailure.Api -> AuthError.Api(AuthApiError.fromCode(code))
    is ApiFailure.Transport -> AuthError.Transport(error)
}
