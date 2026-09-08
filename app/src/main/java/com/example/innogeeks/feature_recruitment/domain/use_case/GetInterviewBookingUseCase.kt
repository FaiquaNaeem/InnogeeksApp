package com.example.innogeeks.feature_recruitment.domain.use_case

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_recruitment.domain.model.InterviewBooking
import com.example.innogeeks.feature_recruitment.domain.repository.RecruitmentRepository

// Not called anywhere yet — same situation as GetTestSlotBookingUseCase.
class GetInterviewBookingUseCase(
    private val recruitmentRepository: RecruitmentRepository
) {
    suspend operator fun invoke(): Result<InterviewBooking, DataError.Network> =
        recruitmentRepository.getInterviewBooking()
}
