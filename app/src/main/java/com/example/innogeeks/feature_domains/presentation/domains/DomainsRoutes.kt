package com.example.innogeeks.feature_domains.presentation.domains

import kotlinx.serialization.Serializable

// Local nav graph scoped to the Domains tab, so the bottom nav bar (owned by MainScaffold) stays put.
@Serializable
internal data object DomainsListRoute

@Serializable
internal data class DomainDetailRoute(val domainId: String)
