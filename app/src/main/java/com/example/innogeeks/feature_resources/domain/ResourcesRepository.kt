package com.example.innogeeks.feature_resources.domain

import com.example.innogeeks.feature_resources.domain.model.ResourceItem

interface ResourcesRepository {
    suspend fun getResources(): Result<List<ResourceItem>>
}
