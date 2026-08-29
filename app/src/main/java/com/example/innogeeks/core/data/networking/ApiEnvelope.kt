package com.example.innogeeks.core.data.networking

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

// Every backend response is wrapped: {"data": {...}} on success, {"error": {...}} on failure.
@Serializable
data class ApiEnvelope<T>(
    val data: T? = null,
    val error: ApiErrorBody? = null
)

@Serializable
data class ApiErrorBody(
    val code: String = "",
    val message: String = "",
    // Zod issues on VALIDATION_ERROR. Kept as raw JSON — the contract says the shape is not stable.
    val details: List<JsonElement> = emptyList()
)
