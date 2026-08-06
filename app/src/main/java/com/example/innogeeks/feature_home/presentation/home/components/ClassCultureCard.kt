package com.example.innogeeks.feature_home.presentation.home.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innogeeks.ui.theme.InnogeeksTheme
import kotlinx.coroutines.delay

@Composable
fun ClassCultureCard(
    moments: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .height(112.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .weight(0.34f)
                .fillMaxHeight()
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Class\nCulture",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                lineHeight = 24.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "SESSIONS & MOMENTS",
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Row(
            modifier = Modifier
                .weight(0.66f)
                .fillMaxHeight()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            moments.forEachIndexed { index, emoji ->
                CalendarCell(
                    emoji = emoji,
                    index = index,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun CalendarCell(
    emoji: String,
    index: Int,
    modifier: Modifier = Modifier
) {
    // Staggered so the cells unfold left to right like a paper calendar.
    var unfolded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(120L * index)
        unfolded = true
    }

    val scaleY by animateFloatAsState(
        targetValue = if (unfolded) 1f else 0f,
        animationSpec = tween(620),
        label = "cellScaleY"
    )
    val alpha by animateFloatAsState(
        targetValue = if (unfolded) 1f else 0f,
        animationSpec = tween(400),
        label = "cellAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .graphicsLayer {
                this.scaleY = scaleY
                this.alpha = alpha
                // Hinge at the top edge so it swings down rather than growing from the middle.
                transformOrigin = TransformOrigin(0.5f, 0f)
            }
            .clip(RoundedCornerShape(10.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondaryContainer
                    )
                )
            )
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = 20.sp)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ClassCultureCardPreview() {
    InnogeeksTheme {
        ClassCultureCard(
            moments = listOf("📡", "🤖", "🏆", "🎤", "🎉"),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}
