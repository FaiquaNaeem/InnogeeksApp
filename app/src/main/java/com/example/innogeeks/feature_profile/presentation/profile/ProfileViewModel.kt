package com.example.innogeeks.feature_profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks.feature_profile.domain.ProfileRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _events = Channel<ProfileEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repository.getProfile()
                .onSuccess { profile ->
                    _state.update { it.copy(isLoading = false, profile = profile) }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load profile. Please try again."
                        )
                    }
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

            // Both buttons are demo-only until auth lands in Phase 2.
            ProfileAction.OnEditClick -> viewModelScope.launch {
                _events.send(ProfileEvent.ShowToast("Profile updated"))
            }

            ProfileAction.OnLogOutClick -> viewModelScope.launch {
                _events.send(ProfileEvent.ShowToast("Logged out (demo)"))
            }
        }
    }
}
