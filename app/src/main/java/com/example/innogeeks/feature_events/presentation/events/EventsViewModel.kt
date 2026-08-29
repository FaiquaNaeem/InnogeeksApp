package com.example.innogeeks.feature_events.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.innogeeks.feature_events.domain.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsViewModel(
    private val repository: EventsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EventsState())
    val state = _state.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repository.getEvents()
                .onSuccess { events ->
                    _state.update { it.copy(isLoading = false, events = events) }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load events. Please try again."
                        )
                    }
                }
        }
    }

    fun onAction(action: EventsAction) {
        when (action) {
            is EventsAction.OnEventClick -> _state.update {
                it.copy(selectedEventId = action.eventId)
            }

            EventsAction.OnBackFromDetail -> _state.update {
                it.copy(selectedEventId = null)
            }

            EventsAction.OnRetry -> loadEvents()
        }
    }
}
