package com.example.innogeeks.feature_profile.presentation.profile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.innogeeks.core.presentation.components.ExpandableRow
import com.example.innogeeks.core.presentation.components.SectionLabel
import com.example.innogeeks.core.presentation.components.StatTile
import com.example.innogeeks.feature_profile.domain.model.DetailEntry
import com.example.innogeeks.feature_profile.domain.model.DomainBadge
import com.example.innogeeks.feature_profile.domain.model.StudentProfile
import com.example.innogeeks.feature_profile.presentation.profile.components.DetailRow
import com.example.innogeeks.feature_profile.presentation.profile.components.DomainBadgeRow
import com.example.innogeeks.feature_profile.presentation.profile.components.ProfileHero
import com.example.innogeeks.ui.theme.InnogeeksTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileRoot(
    hazeState: HazeState,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is ProfileEvent.ShowToast -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ProfileScreen(state = state, hazeState = hazeState, onAction = viewModel::onAction)
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 110.dp)
        )
    }
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    hazeState: HazeState,
    onAction: (ProfileAction) -> Unit
) {
    val scheme = MaterialTheme.colorScheme

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Box
        }

        val profile = state.profile
        if (state.error != null || profile == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.error ?: "Profile unavailable.",
                    color = scheme.error
                )
            }
            return@Box
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .hazeSource(hazeState),
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = scheme.onSurface
                )
            }

            item { ProfileHero(
                initials = profile.initials,
                name = profile.name,
                subtitle = profile.subtitle,
                roleChip = profile.roleChip,
                modifier = Modifier.padding(vertical = 6.dp)
            ) }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatTile(
                        value = profile.domainCount,
                        caption = "Domains",
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        value = profile.eventCount,
                        caption = "Events",
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        value = profile.achievementCount,
                        caption = "Achievements",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                ExpandableRow(
                    title = "Academic Details",
                    subtitle = "Enrollment, branch & semester",
                    isExpanded = state.expandedSection == ProfileSection.ACADEMIC,
                    onToggle = { onAction(ProfileAction.OnSectionToggled(ProfileSection.ACADEMIC)) },
                    leading = { IconChip(emoji = "🎓", background = scheme.primary) }
                ) {
                    profile.academicDetails.forEach { DetailRow(entry = it) }
                }
            }

            item {
                ExpandableRow(
                    title = "Club Involvement",
                    subtitle = "Role, domains & research",
                    isExpanded = state.expandedSection == ProfileSection.CLUB,
                    onToggle = { onAction(ProfileAction.OnSectionToggled(ProfileSection.CLUB)) },
                    leading = { IconChip(emoji = "🚀", background = scheme.secondaryContainer) }
                ) {
                    profile.clubDetails.forEach { DetailRow(entry = it) }
                    SectionLabel(text = "Domains", modifier = Modifier.padding(top = 8.dp))
                    DomainBadgeRow(
                        badges = profile.domainBadges,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.padding(top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ProfileButton(
                        text = "Edit Profile",
                        isPrimary = true,
                        onClick = { onAction(ProfileAction.OnEditClick) },
                        modifier = Modifier.weight(1f)
                    )
                    ProfileButton(
                        text = "Log Out",
                        isPrimary = false,
                        onClick = { onAction(ProfileAction.OnLogOutClick) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun IconChip(
    emoji: String,
    background: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(38.dp)
            .clip(RoundedCornerShape(11.dp))
            .background(background.copy(alpha = 0.2f))
            .border(1.dp, background.copy(alpha = 0.5f), RoundedCornerShape(11.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = 18.sp)
    }
}

@Composable
private fun ProfileButton(
    text: String,
    isPrimary: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(percent = 50))
            .background(if (isPrimary) scheme.primary else scheme.surfaceContainerHigh)
            .border(
                1.dp,
                if (isPrimary) scheme.primary else scheme.outlineVariant,
                RoundedCornerShape(percent = 50)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 11.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = if (isPrimary) scheme.onPrimary else scheme.onSurface
        )
    }
}

private val previewProfile = StudentProfile(
    name = "Ayush",
    initials = "AY",
    subtitle = "ECE-A · 5th Semester · KIET Group of Institutions",
    roleChip = "Innogeeks Core Team",
    domainCount = 2,
    eventCount = 6,
    achievementCount = 4,
    academicDetails = listOf(
        DetailEntry("Enrollment No.", "202401100700051"),
        DetailEntry("Branch", "Electronics & Communication Engg."),
        DetailEntry("Section", "ECE-A"),
        DetailEntry("Semester", "5th"),
        DetailEntry("CGPA", "8.0")
    ),
    clubDetails = listOf(
        DetailEntry("Role", "Core Team · Innogeeks"),
        DetailEntry("Research", "Pressure-measurement device for orthotic design")
    ),
    domainBadges = listOf(DomainBadge("Web Dev", 0), DomainBadge("AR / VR", 3))
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 820)
@Composable
private fun ProfileScreenCollapsedPreview() {
    InnogeeksTheme {
        ProfileScreen(
            state = ProfileState(isLoading = false, profile = previewProfile),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun ProfileScreenAcademicExpandedPreview() {
    InnogeeksTheme {
        ProfileScreen(
            state = ProfileState(
                isLoading = false,
                profile = previewProfile,
                expandedSection = ProfileSection.ACADEMIC
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun ProfileScreenClubExpandedPreview() {
    InnogeeksTheme {
        ProfileScreen(
            state = ProfileState(
                isLoading = false,
                profile = previewProfile,
                expandedSection = ProfileSection.CLUB
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileScreenLoadingPreview() {
    InnogeeksTheme {
        ProfileScreen(state = ProfileState(), hazeState = HazeState(), onAction = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileScreenErrorPreview() {
    InnogeeksTheme {
        ProfileScreen(
            state = ProfileState(isLoading = false, error = "Failed to load profile."),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}
