package com.example.innogeeks.feature_onboarding.presentation.auth.passwordlogin

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.innogeeks.R
import com.example.innogeeks.core.presentation.ObserveAsEvents
import com.example.innogeeks.core.presentation.UiText
import com.example.innogeeks.feature_onboarding.presentation.auth.AuthFormError
import com.example.innogeeks.feature_onboarding.presentation.auth.AuthPasswordField
import com.example.innogeeks.feature_onboarding.presentation.auth.AuthPrimaryButton
import com.example.innogeeks.feature_onboarding.presentation.auth.AuthScaffold
import com.example.innogeeks.ui.theme.InnogeeksTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PasswordLoginRoot(
    collegeEmail: String,
    onNavigateToHome: () -> Unit,
    onNavigateToPasswordReset: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: PasswordLoginViewModel = koinViewModel { parametersOf(collegeEmail) }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is PasswordLoginEvent.NavigateToHome -> onNavigateToHome()
            is PasswordLoginEvent.NavigateToPasswordReset -> onNavigateToPasswordReset()
            is PasswordLoginEvent.NavigateBack -> onNavigateBack()
        }
    }

    PasswordLoginScreen(state = state, onAction = viewModel::onAction)
}

@Composable
fun PasswordLoginScreen(
    state: PasswordLoginState,
    onAction: (PasswordLoginAction) -> Unit,
    modifier: Modifier = Modifier
) {
    AuthScaffold(
        title = "Welcome back",
        subtitle = "Enter the password for ${state.collegeEmail}.",
        modifier = modifier,
        footer = {
            TextButton(onClick = { onAction(PasswordLoginAction.OnBackClick) }) {
                Text(
                    text = "Use a different email",
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    ) {
        AuthPasswordField(
            value = state.password,
            onValueChange = { onAction(PasswordLoginAction.OnPasswordChange(it)) },
            label = "Password",
            isVisible = state.isPasswordVisible,
            onToggleVisibility = { onAction(PasswordLoginAction.OnTogglePasswordVisibility) },
            error = state.passwordError,
            enabled = !state.isSubmitting
        )

        Spacer(Modifier.height(8.dp))

        TextButton(
            onClick = { onAction(PasswordLoginAction.OnForgotPasswordClick) },
            enabled = !state.isSubmitting
        ) {
            Text(
                text = "Forgot password?",
                color = Color.White.copy(alpha = 0.7f)
            )
        }

        Spacer(Modifier.height(4.dp))

        AuthFormError(state.formError)

        Spacer(Modifier.height(16.dp))

        AuthPrimaryButton(
            text = "Log in",
            onClick = { onAction(PasswordLoginAction.OnLoginClick) },
            isSubmitting = state.isSubmitting
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordLoginIdlePreview() {
    InnogeeksTheme {
        PasswordLoginScreen(
            state = PasswordLoginState(collegeEmail = "student@kiet.edu"),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordLoginSubmittingPreview() {
    InnogeeksTheme {
        PasswordLoginScreen(
            state = PasswordLoginState(
                collegeEmail = "student@kiet.edu",
                password = "innogeeks123",
                isSubmitting = true
            ),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordLoginInvalidCredentialsPreview() {
    InnogeeksTheme {
        PasswordLoginScreen(
            state = PasswordLoginState(
                collegeEmail = "student@kiet.edu",
                passwordError = UiText.StringResource(R.string.error_invalid_credentials)
            ),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordLoginAccessDeniedPreview() {
    InnogeeksTheme {
        PasswordLoginScreen(
            state = PasswordLoginState(
                collegeEmail = "student@kiet.edu",
                password = "innogeeks123",
                formError = UiText.StringResource(R.string.error_app_access_denied)
            ),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordLoginOfflinePreview() {
    InnogeeksTheme {
        PasswordLoginScreen(
            state = PasswordLoginState(
                collegeEmail = "student@kiet.edu",
                password = "innogeeks123",
                formError = UiText.StringResource(R.string.error_no_internet)
            ),
            onAction = {}
        )
    }
}
