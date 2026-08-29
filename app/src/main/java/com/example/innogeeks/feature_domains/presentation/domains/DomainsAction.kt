package com.example.innogeeks.feature_domains.presentation.domains

sealed interface DomainsAction {
    data class OnDomainToggled(val domainId: String) : DomainsAction
    data object OnRetry : DomainsAction
}
