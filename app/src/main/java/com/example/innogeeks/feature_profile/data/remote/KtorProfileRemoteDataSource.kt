package com.example.innogeeks.feature_profile.data.remote

import com.example.innogeeks.core.data.networking.delete
import com.example.innogeeks.core.data.networking.get
import com.example.innogeeks.core.data.networking.patch
import com.example.innogeeks.core.data.networking.post
import com.example.innogeeks.core.domain.error.DataError
import com.example.innogeeks.core.domain.util.Result
import com.example.innogeeks.core.domain.util.mapData
import com.example.innogeeks.feature_profile.data.remote.dto.AccountDeletionRequestDto
import com.example.innogeeks.feature_profile.data.remote.dto.AccountDeletionRequestResponseDto
import com.example.innogeeks.feature_profile.data.remote.dto.CancelDeletionResponseDto
import com.example.innogeeks.feature_profile.data.remote.dto.ProfileDto
import com.example.innogeeks.feature_profile.data.remote.dto.ProfileResponseDto
import com.example.innogeeks.feature_profile.data.remote.dto.UpdateProfileRequestDto
import io.ktor.client.HttpClient

class KtorProfileRemoteDataSource(
    private val httpClient: HttpClient
) : ProfileRemoteDataSource {

    override suspend fun getProfile(): Result<ProfileDto, DataError.Network> =
        httpClient.get<ProfileResponseDto>(route = "/api/v1/app/me").mapData { it.data }

    override suspend fun updateProfile(
        request: UpdateProfileRequestDto
    ): Result<ProfileDto, DataError.Network> =
        httpClient.patch<ProfileResponseDto, UpdateProfileRequestDto>(route = "/api/v1/app/me", body = request)
            .mapData { it.data }

    // No request body — POST needs a Request type param regardless, Unit serializes as {}.
    override suspend fun requestAccountDeletion(): Result<AccountDeletionRequestDto, DataError.Network> =
        httpClient.post<AccountDeletionRequestResponseDto, Unit>(route = "/api/v1/app/me/deletion-request", body = Unit)
            .mapData { it.data }

    override suspend fun cancelAccountDeletion(): Result<Unit, DataError.Network> =
        httpClient.delete<CancelDeletionResponseDto>(route = "/api/v1/app/me/deletion-request")
            .mapData { }
}
