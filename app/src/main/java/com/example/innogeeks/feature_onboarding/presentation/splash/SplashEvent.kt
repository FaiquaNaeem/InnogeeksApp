package com.example.innogeeks.feature_onboarding.presentation.splash

// One-shot routing events out of the splash. Login is never forced: a guest lands on Home
// too, because browsing without an account is a first-class state, not a fallback.
sealed interface SplashEvent {
    data object NavigateToIntro : SplashEvent
    data object NavigateToHome : SplashEvent
}
