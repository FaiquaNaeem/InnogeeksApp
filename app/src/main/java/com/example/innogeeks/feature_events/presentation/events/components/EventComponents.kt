package com.example.innogeeks.feature_events.presentation.events.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innogeeks.ui.theme.InnogeeksTheme
import kotlinx.coroutines.delay

// Diagonal-hatch placeholder for the real event image/logo, which no content
// endpoint provides yet (display-only screen, see APP_API_GAPS_RESOLVED.md §6).
@Composable
fun EventImagePlaceholder(
    modifier: Modifier = Modifier,
    height: Dp = 120.dp
) {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(14.dp))
            .background(scheme.surfaceContainerHigh)
            .border(1.dp, scheme.outlineVariant, RoundedCornerShape(14.dp))
            .drawWithContent {
                drawContent()
                val stripeColor = scheme.outlineVariant.copy(alpha = 0.5f)
                val gap = 16.dp.toPx()
                var x = -size.height
                while (x < size.width) {
                    drawLine(
                        color = stripeColor,
                        start = Offset(x, size.height),
                        end = Offset(x + size.height, 0f),
                        strokeWidth = 1.dp.toPx()
                    )
                    x += gap
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "event image / logo",
            style = MaterialTheme.typography.labelSmall,
            color = scheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

// Static pill button, no toggle state — this screen is display-only.
@Composable
private fun LearnMoreButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(percent = 50))
            .background(scheme.surfaceContainer)
            .border(1.dp, scheme.outlineVariant, RoundedCornerShape(percent = 50))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Learn more",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = scheme.onSurface
        )
    }
}

// Image + title + 2-line blurb + Learn more, for both upcoming and past events.
@Composable
fun EventCard(
    title: String,
    blurb: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(scheme.surfaceContainerHigh)
            .border(1.dp, scheme.outlineVariant, RoundedCornerShape(18.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        EventImagePlaceholder()
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = scheme.onSurface
        )
        Text(
            text = blurb,
            style = MaterialTheme.typography.bodySmall,
            color = scheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        LearnMoreButton(onClick = onClick)
    }
}

// Four emoji cells that pop in one after another on a past event's detail page.
@Composable
fun EventPhotoRow(
    modifier: Modifier = Modifier
) {
    val emojis = listOf("📸", "🎤", "🏆", "🎉")

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        emojis.forEachIndexed { index, emoji ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(120L + index * 90L)
                visible = true
            }
            val scale by animateFloatAsState(
                targetValue = if (visible) 1f else 0.6f,
                animationSpec = tween(280),
                label = "photoScale"
            )
            val alpha by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = tween(280),
                label = "photoAlpha"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 20.sp)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EventComponentsPreview() {
    InnogeeksTheme {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            EventCard(
                title = "Hack The Campus 3.0",
                blurb = "A 24-hour campus-wide hackathon open to all branches.",
                onClick = {}
            )
            EventPhotoRow()
        }
    }
}
