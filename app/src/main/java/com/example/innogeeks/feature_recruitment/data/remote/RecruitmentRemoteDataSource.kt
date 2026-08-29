package com.example.innogeeks.feature_recruitment.data.remote

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_recruitment.data.remote.dto.RecruitmentDto

interface RecruitmentRemoteDataSource {
    suspend fun getRecruitmentStatus(): Result<RecruitmentDto, DataError.Network>
}
