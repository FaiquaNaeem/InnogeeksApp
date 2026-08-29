package com.example.innogeeks.feature_profile.domain.use_case

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_profile.domain.model.StudentProfile
import com.example.innogeeks.feature_profile.domain.repository.ProfileRepository

class GetProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(): Result<StudentProfile, DataError.Network> =
        profileRepository.getProfile()
}
