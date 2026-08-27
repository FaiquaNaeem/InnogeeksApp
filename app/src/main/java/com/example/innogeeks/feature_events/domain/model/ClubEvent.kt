package com.example.innogeeks.feature_events.domain.model

data class ClubEvent(
    val id: String,
    val day: String,
    val month: String,
    val title: String,
    val isUpcoming: Boolean,
    // Upcoming events carry a time/location line and a description.
    val timeAndPlace: String = "",
    val description: String = "",
    // Past events carry an attendee count and a recap instead.
    val attendees: Int = 0,
    val recap: String = ""
)
