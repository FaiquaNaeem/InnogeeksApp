package com.example.innogeeks.feature_domains.domain

import com.example.innogeeks.feature_domains.domain.model.Domain

interface DomainsRepository {
    suspend fun getDomains(): Result<List<Domain>>
}
