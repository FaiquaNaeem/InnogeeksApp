package com.example.innogeeks.feature_events.presentation.events.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.innogeeks.feature_events.presentation.events.EventTab
import com.example.innogeeks.ui.theme.InnogeeksTheme
import kotlin.math.roundToInt

// Two-segment control whose highlight pill slides between the segments.
@Composable
fun EventTabs(
    selectedTab: EventTab,
    onTabSelected: (EventTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = EventTab.entries
    val density = LocalDensity.current
    // Track width is measured, not assumed, so the pill lands exactly on a segment.
    var trackWidth by remember { mutableIntStateOf(0) }

    val segmentWidth = trackWidth / tabs.size
    val pillOffset by animateFloatAsState(
        targetValue = (tabs.indexOf(selectedTab) * segmentWidth).toFloat(),
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 420f),
        label = "pillOffset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant,
                RoundedCornerShape(percent = 50)
            )
            .padding(3.dp)
            .onSizeChanged { trackWidth = it.width }
    ) {
        if (segmentWidth > 0) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(pillOffset.roundToInt(), 0) }
                    .width(with(density) { segmentWidth.toDp() })
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(percent = 50))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.22f))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        RoundedCornerShape(percent = 50)
                    )
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            tabs.forEach { tab ->
                val isSelected = tab == selectedTab
                val textColor by animateColorAsState(
                    targetValue = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    animationSpec = tween(240),
                    label = "tabTextColor"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onTabSelected(tab) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (tab == EventTab.UPCOMING) "Upcoming" else "Past",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EventTabsUpcomingPreview() {
    InnogeeksTheme {
        EventTabs(
            selectedTab = EventTab.UPCOMING,
            onTabSelected = {},
            modifier = Modifier.padding(18.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EventTabsPastPreview() {
    InnogeeksTheme {
        EventTabs(
            selectedTab = EventTab.PAST,
            onTabSelected = {},
            modifier = Modifier.padding(18.dp)
        )
    }
}
