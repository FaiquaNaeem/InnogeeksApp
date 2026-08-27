package com.example.innogeeks.feature_recruitment.domain.di

import com.example.innogeeks.feature_recruitment.domain.use_case.GetRecruitmentStatusUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val recruitmentDomainModule = module {
    singleOf(::GetRecruitmentStatusUseCase)
}
