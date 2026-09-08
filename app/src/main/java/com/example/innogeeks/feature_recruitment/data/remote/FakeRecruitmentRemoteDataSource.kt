package com.example.innogeeks.feature_recruitment.data.remote

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_recruitment.data.remote.dto.InterviewBookingDto
import com.example.innogeeks.feature_recruitment.data.remote.dto.InterviewDto
import com.example.innogeeks.feature_recruitment.data.remote.dto.RecruitmentDto
import com.example.innogeeks.feature_recruitment.data.remote.dto.TestSlotBookingDto
import com.example.innogeeks.feature_recruitment.data.remote.dto.TestSlotDto
import kotlinx.coroutines.delay

class FakeRecruitmentRemoteDataSource : RecruitmentRemoteDataSource {

    override suspend fun getRecruitmentStatus(): Result<RecruitmentDto, DataError.Network> {
        delay(800)

        return Result.Success(
            RecruitmentDto(
                paid = true,
                decision = "PENDING",
                decisionNote = null,
                testSlot = TestSlotDto(
                    booked = true,
                    startTime = "2024-08-15T10:00:00Z",
                    endTime = "2024-08-15T11:30:00Z"
                ),
                interview = InterviewDto(
                    assigned = true,
                    startTime = "2024-08-22T09:00:00Z",
                    endTime = "2024-08-22T09:30:00Z",
                    location = "Room 204, Innovation Block",
                    meetingUrl = null
                )
            )
        )
    }

    override suspend fun getTestSlotBooking(): Result<TestSlotBookingDto, DataError.Network> {
        delay(600)
        return Result.Success(
            TestSlotBookingDto(
                testSlotId = "fake-test-slot-1",
                startTime = "2024-08-15T10:00:00Z",
                endTime = "2024-08-15T11:30:00Z",
                bookedAt = "2024-08-01T12:00:00Z"
            )
        )
    }

    override suspend fun getInterviewBooking(): Result<InterviewBookingDto, DataError.Network> {
        delay(600)
        return Result.Success(
            InterviewBookingDto(
                interviewSlotId = "fake-interview-slot-1",
                startTime = "2024-08-22T09:00:00Z",
                endTime = "2024-08-22T09:30:00Z",
                location = "Room 204, Innovation Block",
                meetingUrl = null,
                bookedAt = "2024-08-01T12:00:00Z"
            )
        )
    }
}
