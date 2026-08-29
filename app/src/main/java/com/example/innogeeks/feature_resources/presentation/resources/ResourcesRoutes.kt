package com.example.innogeeks.feature_resources.presentation.resources

import kotlinx.serialization.Serializable

// Local nav graph scoped to the Resources tab: domain picker -> per-domain feed -> resource detail.
@Serializable
internal data object ResourcesListRoute

@Serializable
internal data class ResourceBrowserRoute(val domainId: String)

@Serializable
internal data class ResourceDetailRoute(val resourceId: String)
