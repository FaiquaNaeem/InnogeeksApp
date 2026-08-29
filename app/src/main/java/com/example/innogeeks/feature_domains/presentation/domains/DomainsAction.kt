package com.example.innogeeks.feature_domains.presentation.domains

sealed interface DomainsAction {
    data object OnRetry : DomainsAction
}
