package com.example.innogeeks.feature_recruitment.data.repository

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.core.domain.util.mapData
import com.example.innogeeks.feature_recruitment.data.mapper.toInterviewBooking
import com.example.innogeeks.feature_recruitment.data.mapper.toRecruitmentStatus
import com.example.innogeeks.feature_recruitment.data.mapper.toTestSlotBooking
import com.example.innogeeks.feature_recruitment.data.remote.RecruitmentRemoteDataSource
import com.example.innogeeks.feature_recruitment.domain.model.InterviewBooking
import com.example.innogeeks.feature_recruitment.domain.model.RecruitmentStatus
import com.example.innogeeks.feature_recruitment.domain.model.TestSlotBooking
import com.example.innogeeks.feature_recruitment.domain.repository.RecruitmentRepository

class DefaultRecruitmentRepository(
    private val remoteDataSource: RecruitmentRemoteDataSource
) : RecruitmentRepository {

    override suspend fun getRecruitmentStatus(): Result<RecruitmentStatus, DataError.Network> =
        remoteDataSource.getRecruitmentStatus().mapData { it.toRecruitmentStatus() }

    override suspend fun getTestSlotBooking(): Result<TestSlotBooking, DataError.Network> =
        remoteDataSource.getTestSlotBooking().mapData { it.toTestSlotBooking() }

    override suspend fun getInterviewBooking(): Result<InterviewBooking, DataError.Network> =
        remoteDataSource.getInterviewBooking().mapData { it.toInterviewBooking() }
}
