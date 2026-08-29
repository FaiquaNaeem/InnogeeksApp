package com.example.innogeeks.feature_resources.presentation.resources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks.feature_resources.domain.ResourcesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResourcesViewModel(
    private val repository: ResourcesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ResourcesState())
    val state = _state.asStateFlow()

    private val _events = Channel<ResourcesEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repository.getResourceCategories()
                .onSuccess { categories ->
                    _state.update { it.copy(isLoading = false, categories = categories) }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load resources. Please try again."
                        )
                    }
                }
        }
    }

    fun onAction(action: ResourcesAction) {
        when (action) {
            // Tapping the open category closes it; tapping another swaps which one is open.
            is ResourcesAction.OnCategoryToggled -> {
                _state.update {
                    it.copy(
                        expandedCategoryId = if (it.expandedCategoryId == action.categoryId) {
                            null
                        } else {
                            action.categoryId
                        }
                    )
                }
            }

            is ResourcesAction.OnResourceItemClicked -> {
                viewModelScope.launch {
                    _events.send(ResourcesEvent.OpenUrl(action.url))
                }
            }

            ResourcesAction.OnRetry -> loadCategories()
        }
    }
}
