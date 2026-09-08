package com.example.innogeeks.feature_profile.data.remote.dto

import kotlinx.serialization.Serializable

// Every /api/v1/app response is wrapped as {"data": ...} — this mirrors that envelope,
// same pattern as RecruitmentResponseDto, so the Ktor data source can unwrap it correctly.
@Serializable
data class ProfileResponseDto(
    val data: ProfileDto
)

@Serializable
data class ProfileDto(
    val collegeEmail: String,
    val fullName: String? = null,
    val phone: String? = null,
    val batch: String? = null,
    val year: Int? = null,
    val role: String,
    val domain: String? = null
)

// PATCH /me body — batch/year/role are admin-panel-only, so only these two are self-editable.
@Serializable
data class UpdateProfileRequestDto(
    val fullName: String? = null,
    val phone: String? = null
)

// POST /me/deletion-request response — see APP_API_CONTRACT.md §16.1.
@Serializable
data class AccountDeletionRequestResponseDto(
    val data: AccountDeletionRequestDto
)

@Serializable
data class AccountDeletionRequestDto(
    val deletionRequestedAt: String,
    val scheduledFor: String
)

// DELETE /me/deletion-request response — see APP_API_CONTRACT.md §16.2. `cancelled` is
// always true on success; nothing in the app needs to branch on it, hence no domain model.
@Serializable
data class CancelDeletionResponseDto(
    val data: CancelDeletionDto
)

@Serializable
data class CancelDeletionDto(
    val cancelled: Boolean
)
