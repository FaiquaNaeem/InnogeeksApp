package com.example.innogeeks.feature_profile.presentation.profile

import com.example.innogeeks.core.domain.session.Session
import com.example.innogeeks.core.presentation.UiText
import com.example.innogeeks.feature_profile.domain.model.StudentProfile

data class ProfileState(
    val session: Session = Session.Guest,
    val expandedSection: ProfileSection? = null,
    val isLogOutDialogVisible: Boolean = false,
    val isLoadingProfile: Boolean = false,
    val profile: StudentProfile? = null,
    val profileError: UiText? = null,
    val isEditing: Boolean = false,
    val editableFullName: String = "",
    val editablePhone: String = "",
    val isSaving: Boolean = false,
    val saveError: UiText? = null
)

enum class ProfileSection { ACADEMIC, CLUB }
