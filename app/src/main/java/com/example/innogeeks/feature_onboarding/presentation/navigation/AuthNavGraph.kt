package com.example.innogeeks.feature_onboarding.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.innogeeks.feature_onboarding.presentation.auth.emailgate.EmailGateRoot
import com.example.innogeeks.feature_onboarding.presentation.auth.passwordlogin.PasswordLoginRoot
import com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset.PasswordResetCompleteRoot
import com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset.PasswordResetRequestRoot
import com.example.innogeeks.feature_onboarding.presentation.auth.passwordreset.PasswordResetVerifyCodeRoot
import com.example.innogeeks.feature_onboarding.presentation.auth.setpassword.SetPasswordRoot
import com.example.innogeeks.feature_onboarding.presentation.auth.verifycode.VerifyCodeRoot

// A full-screen graph, not a tab. onAuthenticated pops the whole graph so Back never
// re-enters a completed login; onDismiss returns the guest to whatever they were browsing.
fun NavGraphBuilder.authGraph(
    navController: NavController,
    onAuthenticated: () -> Unit,
    onDismiss: () -> Unit
) {
    navigation<AuthGraphRoute>(startDestination = EmailGateRoute) {
        composable<EmailGateRoute> {
            EmailGateRoot(
                onNavigateToVerifyCode = { email ->
                    navController.navigate(VerifyCodeRoute(collegeEmail = email))
                },
                onNavigateToPasswordLogin = { email ->
                    navController.navigate(PasswordLoginRoute(collegeEmail = email))
                },
                onNavigateBack = onDismiss
            )
        }

        composable<VerifyCodeRoute> { entry ->
            val route = entry.toRoute<VerifyCodeRoute>()
            VerifyCodeRoot(
                collegeEmail = route.collegeEmail,
                onNavigateToSetPassword = { email, token ->
                    navController.navigate(
                        SetPasswordRoute(collegeEmail = email, passwordSetupToken = token)
                    )
                },
                // A 409 means the setup path was wrong for this account, so drop it from the stack.
                onNavigateToPasswordLogin = { email ->
                    navController.navigate(PasswordLoginRoute(collegeEmail = email)) {
                        popUpTo(EmailGateRoute)
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<SetPasswordRoute> { entry ->
            val route = entry.toRoute<SetPasswordRoute>()
            SetPasswordRoot(
                collegeEmail = route.collegeEmail,
                passwordSetupToken = route.passwordSetupToken,
                onNavigateToHome = onAuthenticated,
                // The setup token died, so the code has to be requested again from scratch.
                onRestartVerification = {
                    navController.navigate(VerifyCodeRoute(collegeEmail = route.collegeEmail)) {
                        popUpTo(EmailGateRoute)
                    }
                }
            )
        }

        composable<PasswordLoginRoute> { entry ->
            val route = entry.toRoute<PasswordLoginRoute>()
            PasswordLoginRoot(
                collegeEmail = route.collegeEmail,
                onNavigateToHome = onAuthenticated,
                onNavigateToPasswordReset = {
                    navController.navigate(PasswordResetRequestRoute)
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<PasswordResetRequestRoute> {
            PasswordResetRequestRoot(
                onNavigateToVerifyResetCode = { email ->
                    navController.navigate(PasswordResetVerifyCodeRoute(collegeEmail = email))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<PasswordResetVerifyCodeRoute> { entry ->
            val route = entry.toRoute<PasswordResetVerifyCodeRoute>()
            PasswordResetVerifyCodeRoot(
                collegeEmail = route.collegeEmail,
                onNavigateToCompleteReset = { email, token ->
                    navController.navigate(
                        PasswordResetCompleteRoute(
                            collegeEmail = email,
                            passwordResetToken = token
                        )
                    )
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<PasswordResetCompleteRoute> { entry ->
            val route = entry.toRoute<PasswordResetCompleteRoute>()
            PasswordResetCompleteRoot(
                collegeEmail = route.collegeEmail,
                passwordResetToken = route.passwordResetToken,
                onNavigateToHome = onAuthenticated
            )
        }
    }
}
