package com.example.innogeeks.feature_profile.presentation.profile

import com.example.innogeeks.feature_profile.domain.model.StudentProfile

data class ProfileState(
    val isLoading: Boolean = true,
    val profile: StudentProfile? = null,
    val expandedSection: ProfileSection? = null,
    val error: String? = null
)

enum class ProfileSection { ACADEMIC, CLUB }
