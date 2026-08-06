package com.example.innogeeks.core.domain.session

// Who is using the app right now. Guest is the cold-start default, not an error state.
sealed interface Session {
    data object Guest : Session

    // collegeEmail is the only identity fact we have until a /me endpoint exists.
    data class Registered(val collegeEmail: String) : Session
}
