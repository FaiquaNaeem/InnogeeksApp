package com.example.innogeeks.feature_profile.data.di

import com.example.innogeeks.feature_profile.data.remote.FakeProfileRemoteDataSource
import com.example.innogeeks.feature_profile.data.remote.ProfileRemoteDataSource
import com.example.innogeeks.feature_profile.data.repository.DefaultProfileRepository
import com.example.innogeeks.feature_profile.domain.repository.ProfileRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val profileDataModule = module {
    // Using fake data source for testing without backend
    singleOf(::FakeProfileRemoteDataSource).bind<ProfileRemoteDataSource>()
    // When backend is ready, swap to: singleOf(::KtorProfileRemoteDataSource).bind<ProfileRemoteDataSource>()
    singleOf(::DefaultProfileRepository).bind<ProfileRepository>()
}
