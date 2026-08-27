package com.example.innogeeks.feature_home.domain

import com.example.innogeeks.feature_home.domain.model.Achievement
import com.example.innogeeks.feature_home.domain.model.ClubStats
import com.example.innogeeks.feature_home.domain.model.DomainPreview

interface HomeRepository {
    suspend fun getClubStats(): Result<ClubStats>
    suspend fun getDomains(): Result<List<DomainPreview>>
    suspend fun getAchievements(): Result<List<Achievement>>
    // One inner list per ticker row.
    suspend fun getTickerKeywords(): Result<List<List<String>>>
    suspend fun getCultureMoments(): Result<List<String>>
}
