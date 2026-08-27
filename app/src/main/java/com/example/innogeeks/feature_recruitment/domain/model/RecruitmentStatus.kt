package com.example.innogeeks.feature_recruitment.domain.model

data class RecruitmentStatus(
    val paid: Boolean,
    val decision: Decision,
    val decisionNote: String?,
    val testSlot: TestSlot
)

enum class Decision {
    PENDING,
    SELECTED,
    WAITLISTED,
    REJECTED
}

data class TestSlot(
    val booked: Boolean,
    val startTime: String?, // ISO 8601 string, null when not booked
    val endTime: String?
)
