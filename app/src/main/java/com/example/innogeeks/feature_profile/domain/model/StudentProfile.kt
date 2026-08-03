package com.example.innogeeks.feature_profile.domain.model

data class DetailEntry(
    val label: String,
    val value: String
)

data class DomainBadge(
    val label: String,
    val accentIndex: Int
)

data class StudentProfile(
    val name: String,
    val initials: String,
    val subtitle: String,
    val roleChip: String,
    val domainCount: Int,
    val eventCount: Int,
    val achievementCount: Int,
    val academicDetails: List<DetailEntry>,
    val clubDetails: List<DetailEntry>,
    val domainBadges: List<DomainBadge>
)
