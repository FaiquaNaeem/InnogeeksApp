package com.example.innogeeks.feature_resources.presentation.resources

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.innogeeks.core.presentation.components.ExpandableRow
import com.example.innogeeks.feature_resources.domain.model.ResourceCategory
import com.example.innogeeks.feature_resources.domain.model.ResourceItem
import com.example.innogeeks.feature_resources.domain.model.ResourceType
import com.example.innogeeks.ui.theme.InnogeeksTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResourcesRoot(
    hazeState: HazeState,
    viewModel: ResourcesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is ResourcesEvent.OpenUrl -> uriHandler.openUri(event.url)
            }
        }
    }

    ResourcesScreen(state = state, hazeState = hazeState, onAction = viewModel::onAction)
}

@Composable
fun ResourcesScreen(
    state: ResourcesState,
    hazeState: HazeState,
    onAction: (ResourcesAction) -> Unit
) {
    val scheme = MaterialTheme.colorScheme

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Box
        }

        if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = state.error, color = scheme.error)
                    TextButton(onClick = { onAction(ResourcesAction.OnRetry) }) {
                        Text("Retry")
                    }
                }
            }
            return@Box
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .hazeSource(hazeState),
            contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Resources",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = scheme.onSurface
                    )
                    Text(
                        text = "Guides, links and prep material for the recruitment cycle.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = scheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            items(items = state.categories, key = { it.id }) { category ->
                val isExpanded = state.expandedCategoryId == category.id
                ExpandableRow(
                    title = category.title,
                    subtitle = category.description,
                    isExpanded = isExpanded,
                    onToggle = { onAction(ResourcesAction.OnCategoryToggled(category.id)) }
                ) {
                    ResourceItemList(category = category, onAction = onAction)
                }
            }
        }
    }
}

@Composable
private fun ResourceItemList(
    category: ResourceCategory,
    onAction: (ResourcesAction) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        category.items.forEach { item ->
            ResourceItemRow(
                item = item,
                onClick = { onAction(ResourcesAction.OnResourceItemClicked(item.url)) }
            )
        }
    }
}

@Composable
private fun ResourceItemRow(
    item: ResourceItem,
    onClick: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(scheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.type.icon(),
                contentDescription = null,
                tint = scheme.onSecondaryContainer,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.onSurface
        )
    }
}

private fun ResourceType.icon() = when (this) {
    ResourceType.ARTICLE -> Icons.AutoMirrored.Filled.Article
    ResourceType.VIDEO -> Icons.Filled.PlayCircle
    ResourceType.PDF -> Icons.Filled.PictureAsPdf
    ResourceType.LINK -> Icons.Filled.Link
}

private val previewCategories = listOf(
    ResourceCategory(
        id = "recruitment-prep",
        title = "Recruitment Prep",
        description = "What to expect before your test and interview.",
        items = listOf(
            ResourceItem("1", "Innogeeks Recruitment Guide", ResourceType.ARTICLE, ""),
            ResourceItem("2", "Recruitment FAQ", ResourceType.ARTICLE, "")
        )
    ),
    ResourceCategory(
        id = "club-handbook",
        title = "Club Handbook",
        description = "How Innogeeks runs — domains, events, and expectations.",
        items = listOf(
            ResourceItem("3", "Member Handbook (PDF)", ResourceType.PDF, ""),
            ResourceItem("4", "Join the Discord", ResourceType.LINK, "")
        )
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun ResourcesScreenLoadingPreview() {
    InnogeeksTheme {
        ResourcesScreen(state = ResourcesState(isLoading = true), hazeState = HazeState(), onAction = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun ResourcesScreenErrorPreview() {
    InnogeeksTheme {
        ResourcesScreen(
            state = ResourcesState(isLoading = false, error = "Failed to load resources. Please try again."),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun ResourcesScreenCollapsedPreview() {
    InnogeeksTheme {
        ResourcesScreen(
            state = ResourcesState(isLoading = false, categories = previewCategories),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 900)
@Composable
private fun ResourcesScreenExpandedPreview() {
    InnogeeksTheme {
        ResourcesScreen(
            state = ResourcesState(
                isLoading = false,
                categories = previewCategories,
                expandedCategoryId = "recruitment-prep"
            ),
            hazeState = HazeState(),
            onAction = {}
        )
    }
}
