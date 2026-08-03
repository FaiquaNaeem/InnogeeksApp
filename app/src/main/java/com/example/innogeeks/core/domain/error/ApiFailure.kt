package com.example.innogeeks.core.domain.error

import com.example.innogeeks.core.domain.util.Error

// Splits "the server said no, and told us why" from "we never got a usable reply".
// Contract §2 requires branching on error.code, which DataError.Network cannot express.
sealed interface ApiFailure : Error {
    // A stable machine code from the error envelope. Kept as a raw String here; each
    // feature parses it into its own enum so core stays feature-agnostic.
    data class Api(val code: String) : ApiFailure

    data class Transport(val error: DataError.Network) : ApiFailure
}
