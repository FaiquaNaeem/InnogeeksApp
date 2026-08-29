package com.example.innogeeks.feature_home.domain.model

// One slice of the home domain wheel. wheelLabel is the short caps text drawn on the wedge.
data class DomainPreview(
    val id: String,
    val name: String,
    val wheelLabel: String,
    val blurb: String
)
