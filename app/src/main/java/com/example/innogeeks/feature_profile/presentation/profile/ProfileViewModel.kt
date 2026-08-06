package com.example.innogeeks.feature_profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks.core.domain.session.Session
import com.example.innogeeks.core.domain.session.SessionRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _events = Channel<ProfileEvent>()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            // Sign-out flips this to Guest, so the tab redraws without a manual reload.
            sessionRepository.session.collect { session ->
                _state.update { it.copy(session = session) }
            }
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnSectionToggled -> _state.update {
                it.copy(
                    expandedSection = if (it.expandedSection == action.section) {
                        null
                    } else {
                        action.section
                    }
                )
            }

            ProfileAction.OnLoginClick -> viewModelScope.launch {
                _events.send(ProfileEvent.NavigateToAuth)
            }

            ProfileAction.OnLogOutClick -> _state.update { it.copy(isLogOutDialogVisible = true) }

            ProfileAction.OnLogOutDismissed ->
                _state.update { it.copy(isLogOutDialogVisible = false) }

            ProfileAction.OnLogOutConfirmed -> viewModelScope.launch {
                _state.update { it.copy(isLogOutDialogVisible = false, expandedSection = null) }
                sessionRepository.signOut()
            }
        }
    }
}
