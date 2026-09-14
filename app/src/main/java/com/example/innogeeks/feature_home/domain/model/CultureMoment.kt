package com.example.innogeeks.feature_home.domain.model

data class CultureMoment(
    val id: String,
    val title: String,
    val caption: String,
    // One-line blurb shown under the caption — a taste of the event, not the full writeup.
    val description: String,
    val imageRes: Int,
    // The matching feature_events ClubEvent id, so a tap can open that exact event.
    val eventId: String
)
