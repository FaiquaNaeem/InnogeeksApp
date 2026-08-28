package com.example.innogeeks.feature_recruitment.presentation.tracker

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.innogeeks.feature_recruitment.domain.model.Decision
import com.example.innogeeks.feature_recruitment.domain.model.RecruitmentStatus
import com.example.innogeeks.feature_recruitment.domain.model.TestSlot
import com.example.innogeeks.ui.theme.InnogeeksTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import org.koin.androidx.compose.koinViewModel
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

@Composable
fun TrackerRoot(
    hazeState: HazeState,
    viewModel: TrackerViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TrackerScreen(state = state, hazeState = hazeState, onAction = viewModel::onAction)
}

@Composable
fun TrackerScreen(
    state: TrackerState,
    hazeState: HazeState,
    onAction: (TrackerAction) -> Unit
) {
    val scheme = MaterialTheme.colorScheme

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .hazeSource(hazeState),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 110.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Recruitment Tracker",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = scheme.onSurface
            )
        }

        when {
            state.isLoading -> item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = state.error.asString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = scheme.error,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = { onAction(TrackerAction.OnRetryClick) }) {
                        Text(text = "Retry")
                    }
                }
            }

            state.recruitmentStatus != null -> {
                item {
                    RecruitmentProgressCard(status = state.recruitmentStatus)
                }

                if (state.recruitmentStatus.decisionNote != null) {
                    item {
                        DecisionNoteCard(note = state.recruitmentStatus.decisionNote)
                    }
                }
            }
        }
    }
}

@Composable
private fun RecruitmentProgressCard(
    status: RecruitmentStatus,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(scheme.surfaceContainerHigh)
            .border(1.dp, scheme.outlineVariant, RoundedCornerShape(18.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Stage 1: Registered (always complete)
        StageRow(
            title = "Registered",
            subtitle = "Application submitted",
            isComplete = true
        )

        // Stage 2: Fee Paid
        StageRow(
            title = "Fee Paid",
            subtitle = if (status.paid) "₹50 payment verified" else "Payment pending",
            isComplete = status.paid
        )

        // Stage 3: Test Slot
        val testSubtitle = when {
            status.testSlot.booked -> {
                val start = status.testSlot.startTime?.let { formatDateTime(it) } ?: "TBD"
                "Slot booked: $start"
            }
            else -> "Test slot not booked yet"
        }
        StageRow(
            title = "Aptitude Test",
            subtitle = testSubtitle,
            isComplete = status.testSlot.booked
        )

        // TODO(phase2): wire Interview stage once interview scheduling API exists
        StageRow(
            title = "Interview",
            subtitle = "Pending",
            isComplete = false
        )

        // Stage 5: Decision
        val (decisionTitle, decisionSubtitle) = when (status.decision) {
            Decision.SELECTED -> "Selected" to "Congratulations! You're now a member."
            Decision.WAITLISTED -> "Waitlisted" to "You're on the waitlist. We'll notify you."
            Decision.REJECTED -> "Decision: Not selected" to "Thank you for applying. Keep building!"
            Decision.PENDING -> "Decision" to "Results pending"
        }
        StageRow(
            title = decisionTitle,
            subtitle = decisionSubtitle,
            isComplete = status.decision == Decision.SELECTED
        )
    }
}

@Composable
private fun StageRow(
    title: String,
    subtitle: String,
    isComplete: Boolean,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = if (isComplete) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isComplete) scheme.primary else scheme.outlineVariant,
            modifier = Modifier.size(28.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = if (isComplete) scheme.onSurface else scheme.onSurfaceVariant
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = scheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DecisionNoteCard(
    note: String,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(scheme.secondaryContainer)
            .border(1.dp, scheme.outlineVariant, RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Note from Admin",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = scheme.onSecondaryContainer
        )
        Text(
            text = note,
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.onSecondaryContainer
        )
    }
}

private val trackerDateTimeFormat = LocalDateTime.Format {
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    dayOfMonth()
    chars(", ")
    amPmHour()
    char(':')
    minute()
    char(' ')
    amPmMarker("AM", "PM")
}

private fun formatDateTime(isoString: String): String {
    return try {
        val localDateTime = Instant.parse(isoString).toLocalDateTime(TimeZone.currentSystemDefault())
        trackerDateTimeFormat.format(localDateTime)
    } catch (e: Exception) {
        isoString // fallback to raw string if parsing fails
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun TrackerScreenLoadingPreview() {
    InnogeeksTheme {
        TrackerScreen(
            state = TrackerState(isLoading = true),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun TrackerScreenErrorPreview() {
    InnogeeksTheme {
        TrackerScreen(
            state = TrackerState(error = com.example.innogeeks.core.presentation.UiText.DynamicString("Network error")),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun TrackerScreenPendingPreview() {
    InnogeeksTheme {
        TrackerScreen(
            state = TrackerState(
                recruitmentStatus = RecruitmentStatus(
                    paid = false,
                    decision = Decision.PENDING,
                    decisionNote = null,
                    testSlot = TestSlot(booked = false, startTime = null, endTime = null)
                )
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun TrackerScreenSelectedPreview() {
    InnogeeksTheme {
        TrackerScreen(
            state = TrackerState(
                recruitmentStatus = RecruitmentStatus(
                    paid = true,
                    decision = Decision.SELECTED,
                    decisionNote = "Great work on the test! Welcome to Innogeeks.",
                    testSlot = TestSlot(
                        booked = true,
                        startTime = "2026-08-15T10:00:00Z",
                        endTime = "2026-08-15T11:00:00Z"
                    )
                )
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}
