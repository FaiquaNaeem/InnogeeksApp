package com.example.innogeeks.feature_events.presentation.events

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.innogeeks.feature_events.domain.model.ClubEvent
import com.example.innogeeks.feature_events.presentation.events.components.EventCard
import com.example.innogeeks.feature_events.presentation.events.components.EventImagePlaceholder
import com.example.innogeeks.feature_events.presentation.events.components.EventPhotoRow
import com.example.innogeeks.ui.theme.InnogeeksTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import kotlinx.datetime.LocalDate
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

        val selectedEvent = state.selectedEvent
        if (selectedEvent != null) {
            EventDetailScreen(
                event = selectedEvent,
                hazeState = hazeState,
                onBackClick = { onAction(EventsAction.OnBackFromDetail) }
            )
        } else {
            EventListScreen(state = state, hazeState = hazeState, onAction = onAction)
        }
    }
}

@Composable
private fun EventListScreen(
    state: EventsState,
    hazeState: HazeState,
    onAction: (EventsAction) -> Unit
) {
    val scheme = MaterialTheme.colorScheme

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
                    text = "Everything the club runs — display only, no registration.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = scheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
        }

        items(state.sortedEvents, key = { it.id }) { event ->
            EventCard(
                title = event.title,
                blurb = event.description,
                day = dayLabel(event.date),
                month = monthLabel(event.date),
                attendees = event.attendees,
                cadence = event.cadence,
                onClick = { onAction(EventsAction.OnEventClick(event.id)) },
                hazeState = hazeState
            )
        }
    }
}

@Composable
private fun EventDetailScreen(
    event: ClubEvent,
    hazeState: HazeState,
    onBackClick: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .hazeSource(hazeState)
            .padding(horizontal = 18.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(scheme.surfaceContainerHigh)
                    .clickable(onClick = onBackClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = scheme.onSurface
                )
            }
            Text(
                text = "Event details",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = scheme.onSurface
            )
        }

        EventImagePlaceholder(height = 180.dp)

        Text(
            text = event.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = scheme.onSurface
        )

        Text(
            text = buildString {
                append(dayLabel(event.date))
                append(' ')
                append(monthLabel(event.date))
                append(' ')
                append(event.date.year)
                if (event.timeAndPlace.isNotBlank()) append(" · ${event.timeAndPlace}")
                if (event.cadence.isNotBlank()) append(" · ${event.cadence}")
                if (event.attendees > 0) append(" · ${event.attendees} attendees")
            },
            style = MaterialTheme.typography.bodySmall,
            color = scheme.onSurfaceVariant
        )

        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.onSurfaceVariant
        )

        if (event.attendees > 0) {
            EventPhotoRow()
        }

        Text(
            text = "Display only — no registration action on this page.",
            style = MaterialTheme.typography.labelSmall,
            color = scheme.onSurfaceVariant.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

private fun dayLabel(date: LocalDate): String = date.dayOfMonth.toString().padStart(2, '0')

private fun monthLabel(date: LocalDate): String = date.month.name.take(3)

private val previewEvents = listOf(
    ClubEvent(
        id = "u1",
        title = "Hack The Campus 3.0",
        date = LocalDate(2026, 9, 14),
        timeAndPlace = "10:00 AM · Main Auditorium",
        description = "A 24-hour campus-wide hackathon open to all branches. Teams of up to 4, problem statements released on the day."
    ),
    ClubEvent(
        id = "u2",
        title = "Web Dev Weekly Standup",
        date = LocalDate(2026, 9, 1),
        timeAndPlace = "6:00 PM · Innogeeks Lab",
        description = "Weekly sync for the Web Dev domain — progress updates, blockers, and pairing for the week ahead.",
        isRecurring = true,
        cadence = "Every Tuesday, 6 PM"
    ),
    ClubEvent(
        id = "p1",
        title = "Innogeeks Annual Meet 2026",
        date = LocalDate(2026, 3, 22),
        attendees = 180,
        description = "The club's biggest gathering of the year — recap of the year's wins, domain showcases, and the annual awards."
    ),
    ClubEvent(
        id = "p2",
        title = "Smart India Hackathon — Internal Round",
        date = LocalDate(2026, 2, 10),
        attendees = 96,
        description = "Internal selection round for SIH, with 24 teams pitching problem-statement solutions to a panel of faculty judges."
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 780)
@Composable
private fun EventsScreenListPreview() {
    InnogeeksTheme {
        EventsScreen(
            state = EventsState(isLoading = false, events = previewEvents),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 780)
@Composable
private fun EventsScreenUpcomingDetailPreview() {
    InnogeeksTheme {
        EventsScreen(
            state = EventsState(
                isLoading = false,
                events = previewEvents,
                selectedEventId = "u1"
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 780)
@Composable
private fun EventsScreenPastDetailPreview() {
    InnogeeksTheme {
        EventsScreen(
            state = EventsState(
                isLoading = false,
                events = previewEvents,
                selectedEventId = "p1"
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
