package com.example.innogeeks.feature_home.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.innogeeks.core.domain.session.Session
import com.example.innogeeks.core.domain.session.SessionRepository
import com.example.innogeeks.core.navigation.MainRoute
import com.example.innogeeks.feature_home.presentation.MainScaffold
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

// Extension on NavGraphBuilder — plugs home into the NavHost the same way onboardingGraph does.
fun NavGraphBuilder.homeGraph(
    onNavigateToAuth: () -> Unit
) {
    navigation<MainRoute>(startDestination = MainScaffoldRoute) {
        // The Scaffold is the only destination. Tab switching happens inside it, not via nav.
        composable<MainScaffoldRoute> {
            val sessionRepository = koinInject<SessionRepository>()
            // Collected here so a sign-in or sign-out re-renders every tab with no manual refresh.
            val session by sessionRepository.session
                .collectAsStateWithLifecycle(initialValue = Session.Guest)

            MainScaffold(
                session = session,
                onNavigateToAuth = onNavigateToAuth
            )
        }
    }
}

// Private: nothing outside navigates here directly. MainRoute is the only entry point.
@Serializable
private data object MainScaffoldRoute
