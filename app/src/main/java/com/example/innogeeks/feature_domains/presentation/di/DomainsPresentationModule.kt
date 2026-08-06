package com.example.innogeeks.feature_domains.presentation.di

import com.example.innogeeks.feature_domains.presentation.domains.DomainsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val domainsPresentationModule = module {
    viewModelOf(::DomainsViewModel)
}
