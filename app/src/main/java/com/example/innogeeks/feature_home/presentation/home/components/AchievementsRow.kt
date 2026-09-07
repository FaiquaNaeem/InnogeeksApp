package com.example.innogeeks.feature_home.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innogeeks.feature_home.domain.model.Achievement
import com.example.innogeeks.ui.theme.InnogeeksTheme
import com.example.innogeeks.ui.theme.bodyFontFamily
import com.example.innogeeks.ui.theme.displayFontFamily

import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.PaddingValues
import kotlinx.coroutines.delay

@Composable
fun AchievementsRow(
    achievements: List<Achievement>,
    modifier: Modifier = Modifier
) {
    if (achievements.isEmpty()) return

    val scheme = MaterialTheme.colorScheme
    
    // Start at a multiple of size near the middle of Int.MAX_VALUE to allow infinite scrolling both ways
    val startIndex = (Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % achievements.size)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    
    var isUserInteracting by remember { mutableStateOf(false) }
    // Only the post-interaction resume needs the 10s pause, not the very first start.
    var hasInteractedOnce by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val speedPxPerSec = with(density) { 30.dp.toPx() }

    LaunchedEffect(isUserInteracting) {
        if (isUserInteracting) return@LaunchedEffect

        // Stay paused for 10s after the user lets go before auto-scroll resumes.
        if (hasInteractedOnce) delay(10_000)

        var lastFrameTime = withFrameNanos { it }
        while (true) {
            val frameTime = withFrameNanos { it }
            val delta = (frameTime - lastFrameTime) / 1_000_000_000f
            lastFrameTime = frameTime

            // Negative delta scrolls the list backwards, so content moves to the right.
            listState.scrollBy(-speedPxPerSec * delta)
        }
    }

    // Every card has the same fixed content structure (capped maxLines), so they're
    // naturally equal height already — no need to force it via intrinsics.
    LazyRow(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        val pressed = event.changes.any { it.pressed }
                        if (pressed) hasInteractedOnce = true
                        isUserInteracting = pressed
                    }
                }
            }
            .padding(vertical = 4.dp),
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(11.dp),
        contentPadding = PaddingValues(horizontal = 18.dp),
        userScrollEnabled = true
    ) {
        items(Int.MAX_VALUE) { index ->
            val achievement = achievements[index % achievements.size]
            AchievementCard(achievement = achievement, accent = scheme.primary)
        }
    }
}

@Composable
private fun AchievementCard(
    achievement: Achievement,
    accent: Color,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .width(138.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(accent)
    ) {
        // border-width: 3px 0 3px 6px in Inno_guest.html — inset the inner surface by that much per side, not evenly, so the accent only shows as a left bar plus thin top/bottom lines.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, top = 3.dp, end = 0.dp, bottom = 3.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(scheme.surfaceContainerLowest)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(accent),
                contentAlignment = Alignment.Center
            ) {
                Text(text = achievement.emoji, fontSize = 17.sp)
            }
            Spacer(modifier = Modifier.height(9.dp))
            Text(
                text = achievement.stat,
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = scheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = achievement.label,
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 10.5.sp,
                lineHeight = 14.sp,
                color = scheme.onSurfaceVariant,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AchievementsRowPreview() {
    InnogeeksTheme {
        AchievementsRow(
            achievements = listOf(
                Achievement("a1", "🏆", "Finalist", "Smart India Hackathon"),
                Achievement("a2", "🚀", "Nominee", "NASA Space Apps — Global"),
                Achievement("a3", "🥈", "Top 50", "Flipkart GRiD 5.0"),
                Achievement("a4", "🛠️", "50+", "Projects Shipped"),
                Achievement("a5", "🎓", "40+", "Mentees Guided")
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}
