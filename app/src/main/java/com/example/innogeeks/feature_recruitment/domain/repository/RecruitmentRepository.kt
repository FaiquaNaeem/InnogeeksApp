package com.example.innogeeks.feature_recruitment.domain.repository

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_recruitment.domain.model.RecruitmentStatus

interface RecruitmentRepository {
    suspend fun getRecruitmentStatus(): Result<RecruitmentStatus, DataError.Network>
}
