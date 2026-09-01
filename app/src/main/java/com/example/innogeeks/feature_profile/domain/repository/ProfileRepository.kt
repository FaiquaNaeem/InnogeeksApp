package com.example.innogeeks.feature_profile.domain.repository

import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.feature_profile.domain.model.StudentProfile

interface ProfileRepository {
    suspend fun getProfile(): Result<StudentProfile, DataError.Network>
    suspend fun updateProfile(fullName: String?, phone: String?): Result<StudentProfile, DataError.Network>
}
