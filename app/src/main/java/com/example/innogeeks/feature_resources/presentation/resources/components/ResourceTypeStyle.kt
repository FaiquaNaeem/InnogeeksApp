package com.example.innogeeks.feature_resources.presentation.resources.components

import androidx.compose.ui.graphics.Color
import com.example.innogeeks.feature_resources.domain.model.ResourceType

// One accent + label per resource type, shared by the browser feed, filter bar and detail screen.
internal fun ResourceType.label(): String = when (this) {
    ResourceType.LINK -> "Link"
    ResourceType.PDF -> "PDF"
    ResourceType.VIDEO -> "Video"
    ResourceType.NOTES -> "Notes"
    ResourceType.GITHUB -> "GitHub"
}

internal fun ResourceType.accentColor(): Color = when (this) {
    ResourceType.LINK -> Color(0xFF6CD4F3)
    ResourceType.PDF -> Color(0xFFFF8A78)
    ResourceType.VIDEO -> Color(0xFFBFA0FF)
    ResourceType.NOTES -> Color(0xFF9DE6B8)
    ResourceType.GITHUB -> Color(0xFFC8C8C8)
}

// Per-domain accent for the picker cards, indexed by Domain.accentIndex (reserved for this).
internal val domainAccentPalette = listOf(
    Color(0xFF6CD4F3),
    Color(0xFF82B4FF),
    Color(0xFFA8C7FF),
    Color(0xFFD0BCFF),
    Color(0xFFF9C94C),
    Color(0xFF9DE6B8)
)

internal fun domainAccent(accentIndex: Int): Color =
    domainAccentPalette[accentIndex.mod(domainAccentPalette.size)]
