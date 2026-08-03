package com.example.innogeeks.feature_profile.presentation.profile

import com.example.innogeeks.core.domain.session.Session

data class ProfileState(
    val session: Session = Session.Guest,
    val expandedSection: ProfileSection? = null,
    val isLogOutDialogVisible: Boolean = false
)

enum class ProfileSection { ACADEMIC, CLUB }
