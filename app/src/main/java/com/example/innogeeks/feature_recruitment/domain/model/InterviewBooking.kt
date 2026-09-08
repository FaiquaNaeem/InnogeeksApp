package com.example.innogeeks.feature_recruitment.domain.model

// GET /interview-booking's own model — distinct from RecruitmentStatus.interview, which is a
// lighter summary with no id or assignment timestamp.
data class InterviewBooking(
    val interviewSlotId: String,
    val startTime: String,
    val endTime: String,
    val location: String?,
    val meetingUrl: String?,
    val bookedAt: String
)
