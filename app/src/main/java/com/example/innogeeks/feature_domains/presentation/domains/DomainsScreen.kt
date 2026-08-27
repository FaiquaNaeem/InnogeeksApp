package com.example.innogeeks.feature_domains.presentation.domains

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.innogeeks.R
import com.example.innogeeks.core.presentation.components.ExpandableRow
import com.example.innogeeks.feature_domains.domain.model.Domain
import com.example.innogeeks.feature_domains.domain.model.DomainLead
import com.example.innogeeks.feature_domains.domain.model.DomainStat
import com.example.innogeeks.feature_domains.presentation.domains.components.DomainDetail
import com.example.innogeeks.ui.theme.InnogeeksTheme
import com.example.innogeeks.ui.theme.displayFontFamily
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
    val accents = scheme.secondary


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

        // Collapsed cards use the events grid's 2-column square-tile layout from Inno_guest.html
        // (.ev-grid / .ev-tile); a tapped-open domain still uses the full-width ExpandableRow/DomainDetail
        // pair unchanged, so it spans both columns instead of squeezing into one cell.
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .hazeSource(hazeState),
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 110.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
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

            items(
                items = state.domains,
                key = { it.id },
                span = { domain ->
                    GridItemSpan(if (state.expandedDomainId == domain.id) 2 else 1)
                }
            ) { domain ->
                val accent = accents
                val isExpanded = state.expandedDomainId == domain.id
                if (isExpanded) {
                    ExpandableRow(
                        title = domain.name,
                        subtitle = domain.tagline,
                        isExpanded = true,
                        onToggle = { onAction(DomainsAction.OnDomainToggled(domain.id)) },
                        leading = { DomainEmojiChip(emoji = domain.emoji, accent = accent) }
                    ) {
                        DomainDetail(domain = domain, accent = accent)
                    }
                } else {
                    DomainSquareCard(
                        domain = domain,
                        onClick = { onAction(DomainsAction.OnDomainToggled(domain.id)) }
                    )
                }
            }
        }
    }
}

// Collapsed grid cell — mirrors .ev-card/.ev-tile/.ev-disc/.ev-card-label from the events
// screen section of Inno_guest.html (24px tile radius, 63% centered disc, 12.5px/10.5px label).
@Composable
private fun DomainSquareCard(
    domain: Domain,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(scheme.surfaceContainerLowest)
                .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxSize(0.63f),
                contentAlignment = Alignment.Center
            ) {
                CirclesBackground(modifier = Modifier.fillMaxSize())
                Text(text = domain.emoji, fontSize = 26.sp)
            }
        }
        Column(modifier = Modifier.padding(top = 10.dp, start = 4.dp, end = 4.dp)) {
            Text(
                text = domain.name,
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.5.sp,
                lineHeight = 15.sp,
                color = scheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = domain.tagline,
                fontSize = 10.5.sp,
                color = scheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 3.dp)
            )
        }
    }
}

// Animated background behind the domain emoji, replacing the old hand-coded blob orbit.
@Composable
private fun CirclesBackground(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.circles))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
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
