package com.example.innogeeks.feature_profile.data.repository

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.core.domain.util.mapData
import com.example.innogeeks.feature_profile.data.mapper.toStudentProfile
import com.example.innogeeks.feature_profile.data.remote.ProfileRemoteDataSource
import com.example.innogeeks.feature_profile.data.remote.dto.UpdateProfileRequestDto
import com.example.innogeeks.feature_profile.domain.model.StudentProfile
import com.example.innogeeks.feature_profile.domain.repository.ProfileRepository

class DefaultProfileRepository(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun getProfile(): Result<StudentProfile, DataError.Network> =
        remoteDataSource.getProfile().mapData { it.toStudentProfile() }

    override suspend fun updateProfile(
        fullName: String?,
        phone: String?
    ): Result<StudentProfile, DataError.Network> =
        remoteDataSource.updateProfile(UpdateProfileRequestDto(fullName = fullName, phone = phone))
            .mapData { it.toStudentProfile() }
}
