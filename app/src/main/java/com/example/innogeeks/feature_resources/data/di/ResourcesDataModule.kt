package com.example.innogeeks.feature_resources.data.di

import com.example.innogeeks.feature_resources.data.InMemoryResourcesRepository
import com.example.innogeeks.feature_resources.domain.ResourcesRepository
import org.koin.dsl.module

val resourcesDataModule = module {
    single<ResourcesRepository> { InMemoryResourcesRepository() }
}
