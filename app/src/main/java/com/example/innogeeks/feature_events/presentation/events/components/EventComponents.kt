package com.example.innogeeks.feature_events.presentation.events.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innogeeks.ui.theme.InnogeeksTheme
import kotlinx.coroutines.delay

// Square day/month badge used as the leading slot of every event row.
@Composable
fun EventDateBadge(
    day: String,
    month: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(44.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                RoundedCornerShape(11.dp)
            )
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = month,
            fontSize = 9.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Toggles between Register and a confirmed state. Local-only for now.
@Composable
fun RegisterButton(
    isRegistered: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background by animateColorAsState(
        targetValue = if (isRegistered) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
        } else {
            MaterialTheme.colorScheme.primary
        },
        animationSpec = tween(260),
        label = "registerBg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isRegistered) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onPrimary
        },
        animationSpec = tween(260),
        label = "registerFg"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(percent = 50))
            .background(background)
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                RoundedCornerShape(percent = 50)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isRegistered) "✓ Registered" else "Register",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

// Four emoji cells that pop in one after another when a past event expands.
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
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                EventDateBadge(day = "14", month = "AUG")
                EventDateBadge(day = "05", month = "SEP")
            }
            RegisterButton(isRegistered = false, onClick = {})
            RegisterButton(isRegistered = true, onClick = {})
            EventPhotoRow()
            Box(modifier = Modifier.size(1.dp))
        }
    }
}
