package com.example.innogeeks.feature_home.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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

@Composable
fun AchievementsRow(
    achievements: List<Achievement>,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme

    // IntrinsicSize.Max forces every card to the height of the tallest one, so a two-line label doesn't leave the rest short.
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 4.dp)
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        achievements.forEachIndexed { index, achievement ->
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
            .fillMaxHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(accent)
    ) {
        // border-width: 3px 0 3px 6px in Inno_guest.html — inset the inner surface by that much per side, not evenly, so the accent only shows as a left bar plus thin top/bottom lines.
        Column(
            modifier = Modifier
                .fillMaxSize()
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
