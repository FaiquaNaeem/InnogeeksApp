package com.example.innogeeks.feature_recruitment.data.remote

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_recruitment.data.remote.dto.InterviewBookingDto
import com.example.innogeeks.feature_recruitment.data.remote.dto.RecruitmentDto
import com.example.innogeeks.feature_recruitment.data.remote.dto.TestSlotBookingDto

interface RecruitmentRemoteDataSource {
    suspend fun getRecruitmentStatus(): Result<RecruitmentDto, DataError.Network>

    // NOT_FOUND (404) means not booked yet — a normal empty state, not a failure to retry.
    suspend fun getTestSlotBooking(): Result<TestSlotBookingDto, DataError.Network>
    suspend fun getInterviewBooking(): Result<InterviewBookingDto, DataError.Network>
}
