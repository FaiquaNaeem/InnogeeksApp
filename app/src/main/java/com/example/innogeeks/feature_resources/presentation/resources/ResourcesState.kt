package com.example.innogeeks.feature_resources.presentation.resources

import com.example.innogeeks.feature_resources.domain.model.ResourceCategory

data class ResourcesState(
    val isLoading: Boolean = true,
    val categories: List<ResourceCategory> = emptyList(),
    // Only one category is open at a time, so this is a single id rather than a set.
    val expandedCategoryId: String? = null,
    val error: String? = null
)
