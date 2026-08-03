package com.example.innogeeks.feature_home.presentation.home

sealed interface HomeEvent {
    data object NavigateToProfile : HomeEvent
}
