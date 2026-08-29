package com.example.innogeeks.feature_recruitment.data.mapper

import com.example.innogeeks.feature_recruitment.data.remote.dto.RecruitmentDto
import com.example.innogeeks.feature_recruitment.data.remote.dto.TestSlotDto
import com.example.innogeeks.feature_recruitment.domain.model.Decision
import com.example.innogeeks.feature_recruitment.domain.model.RecruitmentStatus
import com.example.innogeeks.feature_recruitment.domain.model.TestSlot

fun RecruitmentDto.toRecruitmentStatus(): RecruitmentStatus = RecruitmentStatus(
    paid = paid,
    decision = decision.toDecision(),
    decisionNote = decisionNote,
    testSlot = testSlot.toTestSlot()
)

private fun String.toDecision(): Decision = when (this) {
    "PENDING" -> Decision.PENDING
    "SELECTED" -> Decision.SELECTED
    "WAITLISTED" -> Decision.WAITLISTED
    "REJECTED" -> Decision.REJECTED
    else -> Decision.PENDING // fallback for unknown values
}

private fun TestSlotDto.toTestSlot(): TestSlot = TestSlot(
    booked = booked,
    startTime = startTime,
    endTime = endTime
)
