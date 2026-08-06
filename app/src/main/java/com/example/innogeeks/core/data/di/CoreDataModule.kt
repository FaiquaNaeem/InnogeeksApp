package com.example.innogeeks.core.data.di

import com.example.innogeeks.core.data.networking.HttpClientFactory
import com.example.innogeeks.core.data.session.DataStoreSessionRepository
import com.example.innogeeks.core.domain.session.SessionRepository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single<HttpClientEngine>{ OkHttp.create() }
    // Single instance: DataStore throws if the same file is opened twice in one process.
    single { DataStoreSessionRepository(androidContext()) } bind SessionRepository::class
    // Declared after the session repo — the bearer provider reads the token from it.
    single { HttpClientFactory.create(engine = get(), sessionRepository = get()) }
}