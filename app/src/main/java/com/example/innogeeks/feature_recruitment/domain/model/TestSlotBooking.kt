package com.example.innogeeks.feature_recruitment.domain.model

// GET /test-slot-booking's own model — distinct from RecruitmentStatus.testSlot, which is a
// lighter summary with no id or assignment timestamp. All fields are ISO 8601 strings except
// the id, same string-not-Instant convention as the rest of this feature.
data class TestSlotBooking(
    val testSlotId: String,
    val startTime: String,
    val endTime: String,
    val bookedAt: String
)
