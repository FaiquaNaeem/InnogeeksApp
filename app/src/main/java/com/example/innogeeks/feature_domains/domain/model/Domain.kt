package com.example.innogeeks.feature_domains.domain.model

data class DomainStat(
    val value: Int,
    val label: String
)

data class DomainLead(
    val name: String,
    val role: String,
    val initials: String
)

// Accent index maps to a colour pair resolved from the theme at render time.
data class Domain(
    val id: String,
    val name: String,
    val tagline: String,
    val emoji: String,
    val accentIndex: Int,
    val stats: List<DomainStat>,
    val techStack: List<String>,
    val projects: List<String>,
    val lead: DomainLead
)
