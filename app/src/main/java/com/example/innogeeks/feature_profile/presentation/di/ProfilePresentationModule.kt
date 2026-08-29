package com.example.innogeeks.feature_profile.presentation.di

import com.example.innogeeks.feature_profile.presentation.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profilePresentationModule = module {
    viewModelOf(::ProfileViewModel)
}
