package com.example.innogeeks.feature_recruitment.domain.use_case

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_recruitment.domain.model.TestSlotBooking
import com.example.innogeeks.feature_recruitment.domain.repository.RecruitmentRepository

// Not called anywhere yet — TrackerScreen currently reads the lighter summary nested in
// GetRecruitmentStatusUseCase instead. Wire this up if the tracker ever needs the slot id
// or bookedAt timestamp this dedicated endpoint carries.
class GetTestSlotBookingUseCase(
    private val recruitmentRepository: RecruitmentRepository
) {
    suspend operator fun invoke(): Result<TestSlotBooking, DataError.Network> =
        recruitmentRepository.getTestSlotBooking()
}
