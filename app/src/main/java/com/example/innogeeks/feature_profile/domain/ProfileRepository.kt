package com.example.innogeeks.feature_profile.domain

import com.example.innogeeks.feature_profile.domain.model.StudentProfile

interface ProfileRepository {
    suspend fun getProfile(): Result<StudentProfile>
}
