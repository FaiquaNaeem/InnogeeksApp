package com.example.innogeeks.feature_profile.data.di

import com.example.innogeeks.feature_profile.data.remote.FakeProfileRemoteDataSource
import com.example.innogeeks.feature_profile.data.remote.ProfileRemoteDataSource
import com.example.innogeeks.feature_profile.data.repository.DefaultProfileRepository
import com.example.innogeeks.feature_profile.domain.repository.ProfileRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val profileDataModule = module {
    // Backend isn't deployed yet — swap to KtorProfileRemoteDataSource once it's reachable.
    singleOf(::FakeProfileRemoteDataSource).bind<ProfileRemoteDataSource>()
    singleOf(::DefaultProfileRepository).bind<ProfileRepository>()
}
