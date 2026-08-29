package com.example.innogeeks.feature_onboarding.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.innogeeks.feature_onboarding.presentation.intro.IntroRoot
import com.example.innogeeks.feature_onboarding.presentation.splash.SplashRoot

// Splash and Intro only. Login now lives in authGraph, and SignUp is unwired: the app has no
// signup — accounts are created by the backend after offline registration.
fun NavGraphBuilder.onboardingGraph(
    navController: NavController,
    onNavigateToHome: () -> Unit
) {
    navigation<OnboardingGraphRoute>(startDestination = SplashRoute) {
        composable<SplashRoute> {
            SplashRoot(
                onNavigateToIntro = {
                    navController.navigate(IntroRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                },
                onNavigateToHome = onNavigateToHome
            )
        }

        composable<IntroRoute> {
            IntroRoot(onNavigateToHome = onNavigateToHome)
        }
    }
}
