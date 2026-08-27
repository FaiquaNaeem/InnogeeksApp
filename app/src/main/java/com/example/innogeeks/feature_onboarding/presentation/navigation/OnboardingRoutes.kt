package com.example.innogeeks.feature_onboarding.presentation.navigation

import kotlinx.serialization.Serializable

// Type-safe nav routes. @Serializable lets the nav library store them in the back stack.
// LoginRoute and SignUpRoute are unregistered: the app has no signup, and login moved to
// authGraph. Their screens stay on disk but nothing navigates to them.
@Serializable
data object OnboardingGraphRoute

@Serializable
data object SplashRoute

@Serializable
data object IntroRoute
