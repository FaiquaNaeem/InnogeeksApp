package com.example.innogeeks.feature_events.presentation.events

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.innogeeks.core.presentation.components.ExpandableRow
import com.example.innogeeks.feature_events.domain.model.ClubEvent
import com.example.innogeeks.feature_events.presentation.events.components.EventDateBadge
import com.example.innogeeks.feature_events.presentation.events.components.EventPhotoRow
import com.example.innogeeks.feature_events.presentation.events.components.EventTabs
import com.example.innogeeks.feature_events.presentation.events.components.RegisterButton
import com.example.innogeeks.ui.theme.InnogeeksTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventsRoot(
    hazeState: HazeState,
    viewModel: EventsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventsScreen(state = state, hazeState = hazeState, onAction = viewModel::onAction)
}

@Composable
fun EventsScreen(
    state: EventsState,
    hazeState: HazeState,
    onAction: (EventsAction) -> Unit
) {
    val scheme = MaterialTheme.colorScheme

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Box
        }

        if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.error, color = scheme.error)
            }
            return@Box
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .hazeSource(hazeState),
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Events",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = scheme.onSurface
                    )
                    Text(
                        text = "Everything the club has run, and what's coming up next.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = scheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            item {
                EventTabs(
                    selectedTab = state.selectedTab,
                    onTabSelected = { onAction(EventsAction.OnTabSelected(it)) },
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            items(state.visibleEvents, key = { it.id }) { event ->
                ExpandableRow(
                    title = event.title,
                    subtitle = if (event.isUpcoming) {
                        event.timeAndPlace
                    } else {
                        "${event.attendees} attendees"
                    },
                    isExpanded = state.expandedEventId == event.id,
                    onToggle = { onAction(EventsAction.OnEventToggled(event.id)) },
                    leading = { EventDateBadge(day = event.day, month = event.month) }
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = if (event.isUpcoming) event.description else event.recap,
                            style = MaterialTheme.typography.bodyMedium,
                            color = scheme.onSurfaceVariant
                        )
                        if (event.isUpcoming) {
                            RegisterButton(
                                isRegistered = event.id in state.registeredEventIds,
                                onClick = { onAction(EventsAction.OnRegisterClick(event.id)) }
                            )
                        } else {
                            EventPhotoRow()
                        }
                    }
                }
            }
        }
    }
}

private val previewEvents = listOf(
    ClubEvent(
        id = "u1",
        day = "14",
        month = "AUG",
        title = "Hack The Campus 3.0",
        isUpcoming = true,
        timeAndPlace = "10:00 AM · Main Auditorium",
        description = "A 24-hour campus-wide hackathon open to all branches. Teams of up to 4, problem statements released on the day."
    ),
    ClubEvent(
        id = "u2",
        day = "22",
        month = "AUG",
        title = "AI/ML Bootcamp — Session 2",
        isUpcoming = true,
        timeAndPlace = "3:00 PM · Seminar Hall",
        description = "Hands-on session on model evaluation and hyperparameter tuning, continuing from Session 1."
    ),
    ClubEvent(
        id = "p1",
        day = "22",
        month = "MAR",
        title = "Innogeeks Annual Meet 2026",
        isUpcoming = false,
        attendees = 180,
        recap = "The club's biggest gathering of the year — recap of the year's wins, domain showcases, and the annual awards."
    ),
    ClubEvent(
        id = "p2",
        day = "10",
        month = "FEB",
        title = "Smart India Hackathon — Internal Round",
        isUpcoming = false,
        attendees = 96,
        recap = "Internal selection round for SIH, with 24 teams pitching problem-statement solutions to a panel of faculty judges."
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 780)
@Composable
private fun EventsScreenUpcomingPreview() {
    InnogeeksTheme {
        EventsScreen(
            state = EventsState(
                isLoading = false,
                events = previewEvents,
                expandedEventId = "u1"
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 780)
@Composable
private fun EventsScreenRegisteredPreview() {
    InnogeeksTheme {
        EventsScreen(
            state = EventsState(
                isLoading = false,
                events = previewEvents,
                expandedEventId = "u1",
                registeredEventIds = setOf("u1")
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 780)
@Composable
private fun EventsScreenPastPreview() {
    InnogeeksTheme {
        EventsScreen(
            state = EventsState(
                isLoading = false,
                events = previewEvents,
                selectedTab = EventTab.PAST,
                expandedEventId = "p1"
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EventsScreenLoadingPreview() {
    InnogeeksTheme {
        EventsScreen(state = EventsState(), hazeState = HazeState(), onAction = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EventsScreenErrorPreview() {
    InnogeeksTheme {
        EventsScreen(
            state = EventsState(isLoading = false, error = "Failed to load events."),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}
