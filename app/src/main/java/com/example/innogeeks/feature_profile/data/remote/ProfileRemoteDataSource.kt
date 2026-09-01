package com.example.innogeeks.feature_profile.data.remote

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_profile.data.remote.dto.ProfileDto
import com.example.innogeeks.feature_profile.data.remote.dto.UpdateProfileRequestDto

interface ProfileRemoteDataSource {
    suspend fun getProfile(): Result<ProfileDto, DataError.Network>
    suspend fun updateProfile(request: UpdateProfileRequestDto): Result<ProfileDto, DataError.Network>
}
