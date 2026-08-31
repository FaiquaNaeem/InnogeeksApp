package com.example.innogeeks.feature_domains.presentation.domains

import kotlinx.serialization.Serializable

// Local nav graph scoped to the Domains tab's content area.
@Serializable
internal data object DomainsListRoute

@Serializable
internal data class DomainDetailRoute(val domainId: String)
