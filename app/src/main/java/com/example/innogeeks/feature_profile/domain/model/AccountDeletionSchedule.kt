package com.example.innogeeks.feature_profile.domain.model

// ISO 8601 strings, same convention as RecruitmentStatus's startTime/endTime — parsing/
// formatting is a presentation-layer concern, not the domain model's.
data class AccountDeletionSchedule(
    val requestedAt: String,
    val scheduledFor: String
)
