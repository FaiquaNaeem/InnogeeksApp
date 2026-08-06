package com.example.innogeeks.feature_domains.presentation.domains

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks.feature_domains.domain.DomainsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DomainsViewModel(
    private val repository: DomainsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DomainsState())
    val state = _state.asStateFlow()

    init {
        loadDomains()
    }

    private fun loadDomains() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repository.getDomains()
                .onSuccess { domains ->
                    _state.update { it.copy(isLoading = false, domains = domains) }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load domains. Please try again."
                        )
                    }
                }
        }
    }

    fun onAction(action: DomainsAction) {
        when (action) {
            // Tapping the open row closes it; tapping another swaps which one is open.
            is DomainsAction.OnDomainToggled -> {
                _state.update {
                    it.copy(
                        expandedDomainId = if (it.expandedDomainId == action.domainId) {
                            null
                        } else {
                            action.domainId
                        }
                    )
                }
            }

            DomainsAction.OnRetry -> loadDomains()
        }
    }
}
