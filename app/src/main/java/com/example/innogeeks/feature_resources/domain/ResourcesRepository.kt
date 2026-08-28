package com.example.innogeeks.feature_resources.domain

import com.example.innogeeks.feature_resources.domain.model.ResourceCategory

interface ResourcesRepository {
    suspend fun getResourceCategories(): Result<List<ResourceCategory>>
}
