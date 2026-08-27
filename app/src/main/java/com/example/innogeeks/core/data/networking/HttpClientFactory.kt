package com.example.innogeeks.core.data.networking

import com.example.innogeeks.BuildConfig
import com.example.innogeeks.core.domain.session.SessionRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Assembles a fully-configured Ktor HttpClient. An `object` (singleton) exposing a
// create() factory method — matches the skill's DI usage: single { HttpClientFactory.create(get()) }.
object HttpClientFactory {

    // The engine is INJECTED, not hardcoded — Ktor is engine-agnostic (OkHttp, CIO...).
    // Real code passes OkHttp.create(); a test could pass a fake. Depend on the
    // abstraction (HttpClientEngine), not the concrete engine.
    fun create(
        engine: HttpClientEngine,
        sessionRepository: SessionRepository
    ): HttpClient {
        return HttpClient(engine) {

            // HEADERS, never ALL — LogLevel.ALL prints bodies, which would leak passwords,
            // verification codes and tokens into Logcat. Contract §9 forbids that.
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = if (BuildConfig.DEBUG) LogLevel.HEADERS else LogLevel.NONE
            }

            // Attaches the stored token as a Bearer header on every request.
            // No refreshTokens block: the contract has no refresh endpoint, so a 401 is
            // terminal and handled by dropping to guest mode.
            install(Auth) {
                bearer {
                    loadTokens {
                        sessionRepository.currentAccessToken()?.let { token ->
                            BearerTokens(accessToken = token, refreshToken = "")
                        }
                    }
                }
            }

            // Auto-converts JSON <-> Kotlin objects (response.body<Dto>() just works).
            // ignoreUnknownKeys = don't crash if the backend adds a field our DTO lacks.
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        prettyPrint = true
                    }
                )
            }

            // Defaults stamped onto EVERY request (like pre-printed letterhead). Here:
            // "my body is JSON". Later, base URL + auth token can also live here.
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}
