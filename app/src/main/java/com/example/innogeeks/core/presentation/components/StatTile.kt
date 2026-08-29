package com.example.innogeeks.core.presentation.components

import android.content.res.Configuration
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.innogeeks.ui.theme.InnogeeksTheme

// Count-up number + caption tile. Used by Domains detail and Profile.
@Composable
fun StatTile(
    value: Int,
    caption: String,
    modifier: Modifier = Modifier,
    suffix: String = "",
    animate: Boolean = true
) {
    // Starts at 0 and is flipped to the real value on first composition so the number counts up.
    var target by remember(value) { mutableIntStateOf(if (animate) 0 else value) }
    LaunchedEffect(value) { target = value }

    val displayed by animateIntAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = 900, easing = LinearOutSlowInEasing),
        label = "statCountUp"
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "$displayed$suffix",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = caption,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StatTileRowPreview() {
    InnogeeksTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatTile(value = 42, caption = "Members", modifier = Modifier.weight(1f))
            StatTile(value = 12, caption = "Projects", modifier = Modifier.weight(1f))
            StatTile(value = 95, caption = "Placement", suffix = "%", modifier = Modifier.weight(1f))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StatTileStaticPreview() {
    InnogeeksTheme {
        StatTile(
            value = 150,
            caption = "Total Members",
            animate = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}
