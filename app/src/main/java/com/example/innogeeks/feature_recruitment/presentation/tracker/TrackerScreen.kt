package com.example.innogeeks.feature_recruitment.presentation.tracker

import android.content.res.Configuration
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.innogeeks.core.presentation.components.liquidGlass
import com.example.innogeeks.feature_recruitment.domain.model.Decision
import com.example.innogeeks.feature_recruitment.domain.model.RecruitmentStatus
import com.example.innogeeks.feature_recruitment.domain.model.TestSlot
import com.example.innogeeks.ui.theme.InnogeeksTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.flow.collectLatest
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
    onNavigateToResources: () -> Unit = {},
    viewModel: TrackerViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                TrackerEvent.NavigateToResources -> onNavigateToResources()
            }
        }
    }

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
                val stages = state.recruitmentStatus.toJourneyStages()

                item {
                    Text(
                        text = state.recruitmentStatus.statusLine(stages),
                        style = MaterialTheme.typography.bodyMedium,
                        color = scheme.onSurfaceVariant
                    )
                }

                item {
                    JourneyStages(stages = stages, hazeState = hazeState)
                }

                if (state.recruitmentStatus.decisionNote != null) {
                    item {
                        DecisionNoteCard(note = state.recruitmentStatus.decisionNote)
                    }
                }

                val decision = state.recruitmentStatus.decision
                if (decision == Decision.REJECTED || decision == Decision.WAITLISTED) {
                    item {
                        NonSelectionCard(
                            decision = decision,
                            onBrowseResourcesClick = { onAction(TrackerAction.OnBrowseResourcesClick) }
                        )
                    }
                }
            }
        }
    }
}

// One node in the recruitment journey. `decision` is non-null only for the final Decision stage
// and drives its icon/color independent of the generic DONE/CURRENT/PENDING state below.
private enum class StageState { DONE, CURRENT, PENDING }

private data class JourneyStageUi(
    val title: String,
    val subtitle: String,
    val state: StageState,
    val decision: Decision? = null
)

// Interview has no backend field yet (TODO(phase2)), so it can only be inferred "done" once a
// decision exists at all — the app has no other signal that the interview happened.
private fun RecruitmentStatus.toJourneyStages(): List<JourneyStageUi> {
    val afterDecision = decision != Decision.PENDING

    val stages = mutableListOf(
        JourneyStageUi(
            title = "Registered",
            subtitle = "Application submitted",
            state = StageState.DONE
        ),
        JourneyStageUi(
            title = "Fee Paid",
            subtitle = if (paid) "₹50 payment verified" else "Payment pending",
            state = if (paid) StageState.DONE else StageState.PENDING
        ),
        JourneyStageUi(
            title = "Aptitude Test",
            subtitle = when {
                afterDecision -> testSlot.startTime?.let { "Completed ${formatDateTime(it)}" } ?: "Completed"
                testSlot.booked -> "Slot booked: ${testSlot.startTime?.let { formatDateTime(it) } ?: "TBD"}"
                else -> "Test slot not booked yet"
            },
            state = if (afterDecision) StageState.DONE else StageState.PENDING
        ),
        JourneyStageUi(
            title = "Interview",
            subtitle = if (afterDecision) "Completed" else "Scheduled after your test",
            state = if (afterDecision) StageState.DONE else StageState.PENDING
        )
    )

    val (decisionTitle, decisionSubtitle) = when (decision) {
        Decision.SELECTED -> "Selected" to "Congratulations! You're now a member."
        Decision.WAITLISTED -> "Waitlisted" to "You're on the waitlist. We'll notify you."
        Decision.REJECTED -> "Not selected" to "Thank you for applying. Keep building!"
        Decision.PENDING -> "Decision" to "Awaiting result"
    }
    stages += JourneyStageUi(
        title = decisionTitle,
        subtitle = decisionSubtitle,
        state = if (decision == Decision.SELECTED) StageState.DONE else StageState.PENDING,
        decision = decision
    )

    // The first unsettled stage in sequence is where the student's attention belongs right now.
    val currentIndex = stages.indexOfFirst { it.state == StageState.PENDING }
    return if (currentIndex >= 0) {
        stages.mapIndexed { index, stage ->
            if (index == currentIndex) stage.copy(state = StageState.CURRENT) else stage
        }
    } else {
        stages
    }
}

private fun RecruitmentStatus.statusLine(stages: List<JourneyStageUi>): String {
    val current = stages.firstOrNull { it.state == StageState.CURRENT }
    return when {
        decision == Decision.SELECTED -> "You're all set. Welcome to Innogeeks!"
        current != null -> "You're on track. Next up: ${current.title}."
        else -> "Here's where things stand."
    }
}

private val nodeAnchorSize = 24.dp

// A single vertical route through the recruitment stages. The connector line is drawn from each
// node's real measured position (onGloballyPositioned), never guessed coordinates, so it cannot
// misalign with the rows the way a hand-authored path could.
@Composable
private fun JourneyStages(
    stages: List<JourneyStageUi>,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    val nodeCenters = remember { mutableStateMapOf<Int, Float>() }

    Box(modifier = modifier.fillMaxWidth()) {
        val lastSettledIndex = stages.indexOfLast { it.state != StageState.PENDING }
        val startY = nodeCenters[0]
        val doneY = nodeCenters[lastSettledIndex.coerceAtLeast(0)]

        Canvas(modifier = Modifier.matchParentSize()) {
            val x = nodeAnchorSize.toPx() / 2
            val endY = nodeCenters[stages.lastIndex]
            if (startY != null && endY != null) {
                drawLine(
                    color = scheme.outlineVariant,
                    start = Offset(x, startY),
                    end = Offset(x, endY),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            if (startY != null && doneY != null && doneY > startY) {
                drawLine(
                    color = scheme.primary,
                    start = Offset(x, startY),
                    end = Offset(x, doneY),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
            stages.forEachIndexed { index, stage ->
                JourneyStageRow(
                    stage = stage,
                    hazeState = hazeState,
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        val bounds = coordinates.boundsInParent()
                        nodeCenters[index] = bounds.top + bounds.height / 2f
                    }
                )
            }
        }
    }
}

@Composable
private fun JourneyStageRow(
    stage: JourneyStageUi,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    val isCurrent = stage.state == StageState.CURRENT
    val isDone = stage.state == StageState.DONE

    val dotColor = when {
        stage.decision == Decision.REJECTED -> scheme.outline
        stage.decision == Decision.WAITLISTED -> scheme.secondary
        isDone || isCurrent -> scheme.primary
        else -> scheme.outlineVariant
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.size(nodeAnchorSize), contentAlignment = Alignment.Center) {
            // The pulsing ring signals "in progress" — suppressed for a terminal decision
            // (WAITLISTED/REJECTED can be "current" without anything actually being ongoing).
            if (isCurrent && stage.decision == null) {
                val transition = rememberInfiniteTransition(label = "currentRing")
                val ringScale by transition.animateFloat(
                    initialValue = 0.7f,
                    targetValue = 1.3f,
                    animationSpec = infiniteRepeatable(tween(2400, easing = LinearOutSlowInEasing)),
                    label = "ringScale"
                )
                val ringAlpha by transition.animateFloat(
                    initialValue = 0.5f,
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(tween(2400, easing = LinearOutSlowInEasing)),
                    label = "ringAlpha"
                )
                Box(
                    modifier = Modifier
                        .size(nodeAnchorSize)
                        .graphicsLayer(scaleX = ringScale, scaleY = ringScale, alpha = ringAlpha)
                        .border(1.5.dp, scheme.primary, CircleShape)
                )
            }
            Box(
                modifier = Modifier
                    .size(if (isCurrent) 14.dp else 10.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        }

        val textModifier = if (isCurrent) {
            Modifier
                .liquidGlass(hazeState = hazeState, cornerRadius = 16.dp)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        } else {
            Modifier.padding(top = 2.dp)
        }

        Column(modifier = textModifier) {
            Text(
                text = stage.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = when {
                    isCurrent -> scheme.primary
                    isDone -> scheme.onSurface
                    else -> scheme.onSurfaceVariant
                }
            )
            Text(
                text = stage.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = scheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
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

// "What happens now" card for a decision that isn't SELECTED — REJECTED/WAITLISTED users
// still see the progress stages above, but need a clear next step instead of a dead end.
@Composable
private fun NonSelectionCard(
    decision: Decision,
    onBrowseResourcesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme

    val icon: ImageVector
    val headline: String
    val message: String
    when (decision) {
        Decision.WAITLISTED -> {
            icon = Icons.Default.HourglassTop
            headline = "You're on the waitlist"
            message = "We'll notify you the moment a seat opens up — no action needed right now. " +
                "In the meantime, explore what the domains are building."
        }
        Decision.REJECTED -> {
            icon = Icons.Default.SentimentSatisfied
            headline = "Not selected this cycle"
            message = "Thanks for applying — this isn't the end of the story. Keep building and " +
                "look out for the next recruitment cycle."
        }
        else -> return
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(scheme.surfaceContainerHigh)
            .border(1.dp, scheme.outlineVariant, RoundedCornerShape(18.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = scheme.onSurfaceVariant,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = headline,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = scheme.onSurface,
            textAlign = TextAlign.Center
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        OutlinedButton(onClick = onBrowseResourcesClick) {
            Icon(
                imageVector = Icons.Default.FolderOpen,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Text(text = "Browse Resources", modifier = Modifier.padding(start = 8.dp))
        }
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
    } catch (_: Exception) {
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

// Login itself 403s unless the registration is already paid (docs/APP_API_CONTRACT.md), so
// `paid = false` can never actually reach this screen — every preview below is paid = true.
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun TrackerScreenPendingPreview() {
    InnogeeksTheme {
        TrackerScreen(
            state = TrackerState(
                recruitmentStatus = RecruitmentStatus(
                    paid = true,
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
private fun TrackerScreenTestBookedPreview() {
    InnogeeksTheme {
        TrackerScreen(
            state = TrackerState(
                recruitmentStatus = RecruitmentStatus(
                    paid = true,
                    decision = Decision.PENDING,
                    decisionNote = null,
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun TrackerScreenWaitlistedPreview() {
    InnogeeksTheme {
        TrackerScreen(
            state = TrackerState(
                recruitmentStatus = RecruitmentStatus(
                    paid = true,
                    decision = Decision.WAITLISTED,
                    decisionNote = null,
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun TrackerScreenRejectedPreview() {
    InnogeeksTheme {
        TrackerScreen(
            state = TrackerState(
                recruitmentStatus = RecruitmentStatus(
                    paid = true,
                    decision = Decision.REJECTED,
                    decisionNote = "Thanks for your effort — we encourage you to reapply next cycle.",
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
