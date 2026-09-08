package com.example.innogeeks.feature_recruitment.domain.repository

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_recruitment.domain.model.InterviewBooking
import com.example.innogeeks.feature_recruitment.domain.model.RecruitmentStatus
import com.example.innogeeks.feature_recruitment.domain.model.TestSlotBooking

interface RecruitmentRepository {
    suspend fun getRecruitmentStatus(): Result<RecruitmentStatus, DataError.Network>
    suspend fun getTestSlotBooking(): Result<TestSlotBooking, DataError.Network>
    suspend fun getInterviewBooking(): Result<InterviewBooking, DataError.Network>
}
