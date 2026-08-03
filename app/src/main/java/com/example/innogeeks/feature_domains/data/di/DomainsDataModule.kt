package com.example.innogeeks.feature_domains.data.di

import com.example.innogeeks.feature_domains.data.InMemoryDomainsRepository
import com.example.innogeeks.feature_domains.domain.DomainsRepository
import org.koin.dsl.module

val domainsDataModule = module {
    single<DomainsRepository> { InMemoryDomainsRepository() }
}
