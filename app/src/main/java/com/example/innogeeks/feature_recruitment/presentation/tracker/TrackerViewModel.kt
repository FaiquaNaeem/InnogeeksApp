package com.example.innogeeks.feature_recruitment.presentation.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.core.presentation.mapper.toUiText
import com.example.innogeeks.feature_recruitment.domain.use_case.GetRecruitmentStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrackerViewModel(
    private val getRecruitmentStatusUseCase: GetRecruitmentStatusUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TrackerState())
    val state = _state.asStateFlow()

    init {
        loadRecruitmentStatus()
    }

    fun onAction(action: TrackerAction) {
        when (action) {
            TrackerAction.OnRetryClick -> loadRecruitmentStatus()
        }
    }

    private fun loadRecruitmentStatus() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getRecruitmentStatusUseCase()) {
                is Result.Success -> _state.update {
                    it.copy(isLoading = false, recruitmentStatus = result.data)
                }
                is Result.Error -> _state.update {
                    it.copy(isLoading = false, error = result.error.toUiText())
                }
            }
        }
    }
}
