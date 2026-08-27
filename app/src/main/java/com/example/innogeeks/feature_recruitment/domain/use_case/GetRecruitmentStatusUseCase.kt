package com.example.innogeeks.feature_recruitment.domain.use_case

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_recruitment.domain.model.RecruitmentStatus
import com.example.innogeeks.feature_recruitment.domain.repository.RecruitmentRepository

class GetRecruitmentStatusUseCase(
    private val recruitmentRepository: RecruitmentRepository
) {
    suspend operator fun invoke(): Result<RecruitmentStatus, DataError.Network> =
        recruitmentRepository.getRecruitmentStatus()
}
