package com.example.innogeeks.feature_domains.presentation.domains

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.innogeeks.feature_domains.domain.model.Domain
import com.example.innogeeks.feature_domains.domain.model.DomainMember
import com.example.innogeeks.feature_domains.domain.model.DomainMemberRole
import com.example.innogeeks.feature_domains.domain.model.DomainStat
import com.example.innogeeks.core.presentation.components.liquidGlass
import com.example.innogeeks.ui.theme.InnogeeksTheme
import com.example.innogeeks.ui.theme.displayFontFamily
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import org.koin.androidx.compose.koinViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// Nested NavHost scoped to just this tab's content area — MainScaffold's bottom nav
// lives outside this Box, so it stays on screen while the list/detail pair slide underneath it.
@Composable
fun DomainsRoot(
    hazeState: HazeState,
    viewModel: DomainsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DomainsListRoute,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        composable<DomainsListRoute> {
            DomainsScreen(
                state = state,
                hazeState = hazeState,
                onDomainClick = { domainId -> navController.navigate(DomainDetailRoute(domainId)) }
            )
        }
        composable<DomainDetailRoute> { backStackEntry ->
            val route: DomainDetailRoute = backStackEntry.toRoute()
            val domain = state.domains.find { it.id == route.domainId }
            if (domain != null) {
                DomainDetailScreen(
                    domain = domain,
                    hazeState = hazeState,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun DomainsScreen(
    state: DomainsState,
    hazeState: HazeState,
    onDomainClick: (String) -> Unit
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

        // Fixed (non-scrolling) 2-column grid whose rows share the leftover height equally,
        // so the 6 domain cards always fill the screen edge-to-edge with no dead space below.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .hazeSource(hazeState)
                .padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 110.dp)
        ) {
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
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                state.domains.chunked(2).forEach { rowDomains ->
                    Row(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        rowDomains.forEach { domain ->
                            DomainSquareCard(
                                domain = domain,
                                hazeState = hazeState,
                                onClick = { onDomainClick(domain.id) },
                                modifier = Modifier.weight(1f).fillMaxHeight()
                            )
                        }
                        if (rowDomains.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
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
    hazeState: HazeState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    Column(modifier = modifier.clickable(onClick = onClick)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .liquidGlass(hazeState = hazeState, cornerRadius = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            BlobBackground(seed = domain.id.hashCode(), modifier = Modifier.fillMaxSize())
            Text(text = domain.emoji, fontSize = 26.sp)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 4.dp, end = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = domain.name,
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.5.sp,
                lineHeight = 15.sp,
                color = scheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = domain.tagline,
                fontSize = 10.5.sp,
                color = scheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

// Animated ambient-gradient background behind the domain emoji: one deep wash blob
// anchors the composition, a mid-tone blob adds body, and a small bright blob acts
// as a highlight accent — normal alpha blending (not additive) so overlaps deepen
// in color instead of blowing out to a white hotspot.
private data class BlobSpec(
    val color: Color,
    val peakAlpha: Float,
    val radiusScale: Float,
    val orbitReachScale: Float,
    val orbitCenterOffset: Offset, // fraction of container size, relative to center
    val periodMs: Int
)

private val blobSpecs = listOf(
    BlobSpec(Color(0xFF0B4C63), 0.50f, 0.78f, 0.14f, Offset(0f, 0f), 7200),
    BlobSpec(Color(0xFF1AA6C9), 0.38f, 0.58f, 0.26f, Offset(0.10f, -0.06f), 5600),
    BlobSpec(Color(0xFF7FE3FF), 0.32f, 0.34f, 0.34f, Offset(-0.12f, 0.10f), 4400)
)

@Composable
private fun BlobBackground(seed: Int, modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "blob")
    val phases = remember(seed) { List(blobSpecs.size) { Random(seed + it).nextInt(0, 4000) } }
    val angles = blobSpecs.indices.map { i ->
        infinite.animateFloat(
            initialValue = 0f,
            targetValue = (2 * PI).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = blobSpecs[i].periodMs, easing = LinearEasing),
                initialStartOffset = StartOffset(phases[i])
            ),
            label = "angle$i"
        )
    }
    Canvas(modifier = modifier.blur(20.dp)) {
        val base = Offset(size.width / 2f, size.height / 2f)
        blobSpecs.forEachIndexed { i, spec ->
            val t = angles[i].value + i * 2.1f
            val orbitCenter = base + Offset(spec.orbitCenterOffset.x * size.width, spec.orbitCenterOffset.y * size.height)
            val reach = size.minDimension * spec.orbitReachScale
            val radius = size.minDimension * spec.radiusScale
            val cx = orbitCenter.x + cos(t) * reach
            val cy = orbitCenter.y + sin(t * 1.3f) * reach
            drawCircle(
                brush = Brush.radialGradient(
                    0f to spec.color.copy(alpha = spec.peakAlpha),
                    0.45f to spec.color.copy(alpha = spec.peakAlpha * 0.55f),
                    1f to spec.color.copy(alpha = 0f),
                    center = Offset(cx, cy),
                    radius = radius
                ),
                radius = radius,
                center = Offset(cx, cy)
            )
        }
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
        members = listOf(
            DomainMember("Priya Sharma", "PS", DomainMemberRole.COORDINATOR),
            DomainMember("Rahul Deshmukh", "RD", DomainMemberRole.COORDINATOR),
            DomainMember("Ananya Iyer", "AI", DomainMemberRole.TEAM)
        )
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
        members = listOf(
            DomainMember("Rohan Verma", "RV", DomainMemberRole.COORDINATOR),
            DomainMember("Kavya Reddy", "KR", DomainMemberRole.COORDINATOR),
            DomainMember("Arjun Nanda", "AN", DomainMemberRole.TEAM)
        )
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
        members = listOf(
            DomainMember("Ananya Gupta", "AG", DomainMemberRole.COORDINATOR),
            DomainMember("Vikram Rao", "VR", DomainMemberRole.COORDINATOR),
            DomainMember("Nisha Bhat", "NB", DomainMemberRole.TEAM)
        )
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun DomainsScreenCollapsedPreview() {
    InnogeeksTheme {
        DomainsScreen(
            state = DomainsState(isLoading = false, domains = previewDomainList),
            hazeState = HazeState(),
            onDomainClick = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DomainsScreenLoadingPreview() {
    InnogeeksTheme {
        DomainsScreen(state = DomainsState(), hazeState = HazeState(), onDomainClick = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DomainsScreenErrorPreview() {
    InnogeeksTheme {
        DomainsScreen(
            state = DomainsState(isLoading = false, error = "Failed to load domains."),
            hazeState = HazeState(),
            onDomainClick = {}
        )
    }
}
