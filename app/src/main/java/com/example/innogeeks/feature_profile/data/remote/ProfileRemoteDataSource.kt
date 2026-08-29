package com.example.innogeeks.feature_profile.data.remote

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_profile.data.remote.dto.ProfileDto

interface ProfileRemoteDataSource {
    suspend fun getProfile(): Result<ProfileDto, DataError.Network>
}
