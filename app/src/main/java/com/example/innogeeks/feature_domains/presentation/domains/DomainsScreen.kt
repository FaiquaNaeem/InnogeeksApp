package com.example.innogeeks.feature_domains.presentation.domains

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.innogeeks.core.presentation.components.ExpandableRow
import com.example.innogeeks.feature_domains.domain.model.Domain
import com.example.innogeeks.feature_domains.domain.model.DomainLead
import com.example.innogeeks.feature_domains.domain.model.DomainStat
import com.example.innogeeks.feature_domains.presentation.domains.components.DomainDetail
import com.example.innogeeks.ui.theme.InnogeeksTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import org.koin.androidx.compose.koinViewModel

@Composable
fun DomainsRoot(
    hazeState: HazeState,
    viewModel: DomainsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DomainsScreen(state = state, hazeState = hazeState, onAction = viewModel::onAction)
}

@Composable
fun DomainsScreen(
    state: DomainsState,
    hazeState: HazeState,
    onAction: (DomainsAction) -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    // Accent per domain, in the same order the mockup uses.
    val accents = listOf(
        scheme.primary,
        scheme.secondary,
        scheme.tertiary,
        scheme.secondaryContainer,
        scheme.primaryContainer,
        scheme.onPrimaryContainer
    )

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
                        text = "Domains",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = scheme.onSurface
                    )
                    Text(
                        text = "Tap any domain to see its full picture — team, stack, and wins.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = scheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            items(state.domains, key = { it.id }) { domain ->
                val accent = accents[domain.accentIndex % accents.size]
                ExpandableRow(
                    title = domain.name,
                    subtitle = domain.tagline,
                    isExpanded = state.expandedDomainId == domain.id,
                    onToggle = { onAction(DomainsAction.OnDomainToggled(domain.id)) },
                    leading = { DomainEmojiChip(emoji = domain.emoji, accent = accent) }
                ) {
                    DomainDetail(domain = domain, accent = accent)
                }
            }
        }
    }
}

@Composable
private fun DomainEmojiChip(
    emoji: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(38.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(accent.copy(alpha = 0.16f))
            .border(1.dp, accent.copy(alpha = 0.45f), RoundedCornerShape(11.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = 18.sp)
    }
}

internal val previewDomainList = listOf(
    Domain(
        id = "webd",
        name = "Web Dev",
        tagline = "React, Node & everything between",
        emoji = "🌐",
        accentIndex = 0,
        stats = listOf(
            DomainStat(18, "Members"),
            DomainStat(12, "Projects"),
            DomainStat(3, "Hackathon Wins")
        ),
        techStack = listOf("React", "Node.js", "Tailwind", "MongoDB", "TypeScript"),
        projects = listOf("Innogeeks Website", "Event Portal", "Alumni Network"),
        lead = DomainLead("Priya Sharma", "Domain Lead · Web Dev", "PS")
    ),
    Domain(
        id = "appd",
        name = "App Dev",
        tagline = "Native & cross-platform builders",
        emoji = "📱",
        accentIndex = 1,
        stats = listOf(
            DomainStat(14, "Members"),
            DomainStat(9, "Apps Shipped"),
            DomainStat(2, "Hackathon Wins")
        ),
        techStack = listOf("Kotlin", "Flutter", "Firebase", "Jetpack Compose"),
        projects = listOf("Innogeeks App", "Campus Navigator", "Mess Menu Tracker"),
        lead = DomainLead("Rohan Verma", "Domain Lead · App Dev", "RV")
    ),
    Domain(
        id = "ml",
        name = "Machine Learning",
        tagline = "Models, data & leaderboard chasing",
        emoji = "🧠",
        accentIndex = 2,
        stats = listOf(
            DomainStat(11, "Members"),
            DomainStat(7, "Models Trained"),
            DomainStat(4, "Kaggle Medals")
        ),
        techStack = listOf("Python", "TensorFlow", "PyTorch", "Scikit-learn", "Pandas"),
        projects = listOf("Attendance Face-ID", "Crop Yield Predictor", "Chatbot Assistant"),
        lead = DomainLead("Ananya Gupta", "Domain Lead · ML", "AG")
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun DomainsScreenCollapsedPreview() {
    InnogeeksTheme {
        DomainsScreen(
            state = DomainsState(isLoading = false, domains = previewDomainList),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun DomainsScreenExpandedPreview() {
    InnogeeksTheme {
        DomainsScreen(
            state = DomainsState(
                isLoading = false,
                domains = previewDomainList,
                expandedDomainId = "webd"
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DomainsScreenLoadingPreview() {
    InnogeeksTheme {
        DomainsScreen(state = DomainsState(), hazeState = HazeState(), onAction = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DomainsScreenErrorPreview() {
    InnogeeksTheme {
        DomainsScreen(
            state = DomainsState(isLoading = false, error = "Failed to load domains."),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}
