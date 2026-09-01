package com.example.innogeeks.feature_events.domain.model

import kotlinx.datetime.LocalDate

data class ClubEvent(
    val id: String,
    val title: String,
    // For a recurring event, this is its next occurrence — real dates come from the backend later.
    val date: LocalDate,
    val timeAndPlace: String = "",
    val description: String = "",
    // 0 means no attendance data yet (the event hasn't happened).
    val attendees: Int = 0,
    val isRecurring: Boolean = false,
    // e.g. "Every Tuesday, 6 PM" — only meaningful when isRecurring is true.
    val cadence: String = "",
    // Instagram/LinkedIn/Devfolio post for the event, when one exists.
    val eventLink: String = "",
    val cardImageRes: Int? = null,
    val galleryImageRes: List<Int> = emptyList()
)
