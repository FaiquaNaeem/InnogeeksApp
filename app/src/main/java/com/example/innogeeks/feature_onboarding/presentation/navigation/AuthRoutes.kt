package com.example.innogeeks.feature_onboarding.presentation.navigation

import kotlinx.serialization.Serializable

// The college email travels as a route argument rather than shared mutable state, which is
// how §2's "preserve the college email across the complete flow" is satisfied.
@Serializable
data object AuthGraphRoute

@Serializable
data object EmailGateRoute

@Serializable
data class VerifyCodeRoute(val collegeEmail: String)

@Serializable
data class SetPasswordRoute(val collegeEmail: String, val passwordSetupToken: String)

@Serializable
data class PasswordLoginRoute(val collegeEmail: String)
