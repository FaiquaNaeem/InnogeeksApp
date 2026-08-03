package com.example.innogeeks.core.domain.session

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    // Emits on every sign-in/sign-out so the whole UI tree reacts without manual refresh.
    val session: Flow<Session>

    // Data-layer only — the Ktor bearer provider reads this. Never expose the token to presentation.
    suspend fun currentAccessToken(): String?

    suspend fun signIn(accessToken: String, collegeEmail: String)

    suspend fun signOut()

    suspend fun hasSeenIntro(): Boolean

    suspend fun markIntroSeen()
}
