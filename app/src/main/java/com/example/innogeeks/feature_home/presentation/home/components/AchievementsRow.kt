package com.example.innogeeks.feature_home.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.innogeeks.feature_home.domain.model.Achievement
import com.example.innogeeks.ui.theme.InnogeeksTheme

@Composable
fun AchievementsRow(
    achievements: List<Achievement>,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    val accents = listOf(
        scheme.primary,
        scheme.secondary,
        scheme.tertiary,
        scheme.secondaryContainer,
        scheme.onPrimaryContainer
    )

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        itemsIndexed(achievements) { index, achievement ->
            AchievementCard(
                achievement = achievement,
                accent = accents[index % accents.size]
            )
        }
    }
}

@Composable
private fun AchievementCard(
    achievement: Achievement,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(150.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(14.dp))
    ) {
        // Accent bar down the left edge.
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(accent)
                .align(Alignment.CenterStart)
        )

        Column(
            modifier = Modifier.padding(start = 20.dp, top = 14.dp, end = 14.dp, bottom = 14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = achievement.emoji, fontSize = 20.sp)
            Text(
                text = achievement.stat,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = achievement.label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
                Achievement("a3", "🥈", "Top 50", "Flipkart GRiD 5.0")
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}
