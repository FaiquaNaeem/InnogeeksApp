package com.example.innogeeks.feature_resources.presentation.di

import com.example.innogeeks.feature_resources.presentation.resources.ResourcesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val resourcesPresentationModule = module {
    viewModelOf(::ResourcesViewModel)
}
