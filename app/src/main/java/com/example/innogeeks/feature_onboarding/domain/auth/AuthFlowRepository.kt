package com.example.innogeeks.feature_onboarding.domain.auth

import com.example.innogeeks.core.domain.util.EmptyResult
import com.example.innogeeks.core.domain.util.Result

// What the four auth screens talk to. Deliberately returns no token: the access token is
// stored inside the data layer, so presentation can never see or log it (§9).
interface AuthFlowRepository {

    suspend fun checkEmail(collegeEmail: String): Result<NextStep, AuthError>

    suspend fun requestVerificationCode(collegeEmail: String): EmptyResult<AuthError>

    suspend fun verifyCode(collegeEmail: String, code: String): Result<String, AuthError>

    // Both of these end with the user signed in.
    suspend fun setPassword(
        collegeEmail: String,
        passwordSetupToken: String,
        password: String
    ): EmptyResult<AuthError>

    suspend fun login(collegeEmail: String, password: String): EmptyResult<AuthError>
}
