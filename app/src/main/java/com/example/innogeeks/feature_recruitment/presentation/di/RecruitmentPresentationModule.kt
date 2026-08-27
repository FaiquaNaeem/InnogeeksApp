package com.example.innogeeks.feature_recruitment.presentation.di

import com.example.innogeeks.feature_recruitment.presentation.tracker.TrackerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recruitmentPresentationModule = module {
    viewModelOf(::TrackerViewModel)
}
