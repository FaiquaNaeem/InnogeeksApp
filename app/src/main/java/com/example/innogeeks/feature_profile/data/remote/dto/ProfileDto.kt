package com.example.innogeeks.feature_profile.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val collegeEmail: String,
    val fullName: String? = null,
    val phone: String? = null,
    val batch: String? = null,
    val year: Int? = null,
    val role: String
)
