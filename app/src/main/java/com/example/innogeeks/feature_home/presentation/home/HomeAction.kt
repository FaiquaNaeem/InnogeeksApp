package com.example.innogeeks.feature_home.presentation.home

sealed interface HomeAction {
    data class OnDomainSelected(val domainId: String) : HomeAction
    data object OnProfileClick : HomeAction
}
