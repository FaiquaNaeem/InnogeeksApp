package com.example.innogeeks.feature_profile.data.di

import com.example.innogeeks.feature_profile.data.InMemoryProfileRepository
import com.example.innogeeks.feature_profile.domain.ProfileRepository
import org.koin.dsl.module

val profileDataModule = module {
    single<ProfileRepository> { InMemoryProfileRepository() }
}
