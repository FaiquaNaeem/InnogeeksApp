package com.example.innogeeks.feature_recruitment.data.di

import com.example.innogeeks.feature_recruitment.data.remote.FakeRecruitmentRemoteDataSource
import com.example.innogeeks.feature_recruitment.data.remote.RecruitmentRemoteDataSource
import com.example.innogeeks.feature_recruitment.data.repository.DefaultRecruitmentRepository
import com.example.innogeeks.feature_recruitment.domain.repository.RecruitmentRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val recruitmentDataModule = module {
    // Using fake data source for testing without backend
    singleOf(::FakeRecruitmentRemoteDataSource).bind<RecruitmentRemoteDataSource>()
    // When backend is ready, swap to: singleOf(::KtorRecruitmentRemoteDataSource).bind<RecruitmentRemoteDataSource>()
    singleOf(::DefaultRecruitmentRepository).bind<RecruitmentRepository>()
}
