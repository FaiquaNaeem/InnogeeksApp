package com.example.innogeeks.feature_resources.domain.model

data class ResourceCategory(
    val id: String,
    val title: String,
    val description: String,
    val items: List<ResourceItem>
)

data class ResourceItem(
    val id: String,
    val title: String,
    val type: ResourceType,
    val url: String
)

enum class ResourceType {
    ARTICLE,
    VIDEO,
    PDF,
    LINK
}
