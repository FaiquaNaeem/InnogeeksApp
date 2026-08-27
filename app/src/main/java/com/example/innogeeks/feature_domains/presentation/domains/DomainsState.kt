package com.example.innogeeks.feature_domains.presentation.domains

import com.example.innogeeks.feature_domains.domain.model.Domain

data class DomainsState(
    val isLoading: Boolean = true,
    val domains: List<Domain> = emptyList(),
    // Only one row is open at a time, so this is a single id rather than a set.
    val expandedDomainId: String? = null,
    val error: String? = null
)
