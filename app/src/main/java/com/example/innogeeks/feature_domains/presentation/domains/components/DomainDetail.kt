package com.example.innogeeks.feature_domains.presentation.domains.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.innogeeks.core.presentation.components.SectionLabel
import com.example.innogeeks.core.presentation.components.StatTile
import com.example.innogeeks.feature_domains.domain.model.Domain
import com.example.innogeeks.feature_domains.domain.model.DomainLead
import com.example.innogeeks.feature_domains.domain.model.DomainStat
import com.example.innogeeks.ui.theme.InnogeeksTheme
import kotlinx.coroutines.delay

// Everything shown inside an expanded domain row.
@Composable
fun DomainDetail(
    domain: Domain,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DomainSignatureIcon(domainId = domain.id, accent = accent)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            domain.stats.forEach { stat ->
                StatTile(
                    value = stat.value,
                    caption = stat.label,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SectionLabel("Tech Stack")
            StaggeredChipFlow(items = domain.techStack, accent = accent)
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            SectionLabel("Notable Projects")
            domain.projects.forEachIndexed { index, project ->
                ProjectRow(text = project, accent = accent, index = index)
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        LeadFooter(lead = domain.lead, accent = accent)
    }
}

// Chips pop in one after another, wrapping onto as many lines as needed.
@Composable
private fun StaggeredChipFlow(
    items: List<String>,
    accent: Color,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items.forEachIndexed { index, item ->
            var visible by remember(items) { mutableStateOf(false) }
            LaunchedEffect(items) {
                delay(60L * index)
                visible = true
            }
            val scale by animateFloatAsState(
                targetValue = if (visible) 1f else 0.7f,
                animationSpec = tween(260),
                label = "chipScale"
            )
            val alpha by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = tween(260),
                label = "chipAlpha"
            )

            Text(
                text = item,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .clip(RoundedCornerShape(percent = 50))
                    .background(accent.copy(alpha = 0.14f))
                    .border(1.dp, accent.copy(alpha = 0.45f), RoundedCornerShape(percent = 50))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
    }
}

// Slides in from the left, staggered by position.
@Composable
private fun ProjectRow(
    text: String,
    accent: Color,
    index: Int,
    modifier: Modifier = Modifier
) {
    var visible by remember(text) { mutableStateOf(false) }
    LaunchedEffect(text) {
        delay(80L * index)
        visible = true
    }
    val offsetX by animateFloatAsState(
        targetValue = if (visible) 0f else -24f,
        animationSpec = tween(300),
        label = "projectOffset"
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(300),
        label = "projectAlpha"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationX = offsetX
                this.alpha = alpha
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(accent)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LeadFooter(
    lead: DomainLead,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.18f))
                .border(1.dp, accent.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lead.initials,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = accent
            )
        }
        Column {
            Text(
                text = lead.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = lead.role,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 620)
@Composable
private fun DomainDetailPreview() {
    InnogeeksTheme {
        DomainDetail(
            domain = Domain(
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
            accent = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
    }
}
