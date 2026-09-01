package com.example.innogeeks.feature_profile.domain.di

import com.example.innogeeks.feature_profile.domain.use_case.GetProfileUseCase
import com.example.innogeeks.feature_profile.domain.use_case.UpdateProfileUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val profileDomainModule = module {
    singleOf(::GetProfileUseCase)
    singleOf(::UpdateProfileUseCase)
}
