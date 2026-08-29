package com.example.innogeeks.feature_domains.domain.model

data class DomainStat(
    val value: Int,
    val label: String
)

// COORDINATOR = 2nd-years running the domain day-to-day. TEAM = 3rd-years who make up the rest of it.
enum class DomainMemberRole {
    COORDINATOR,
    TEAM
}

data class DomainMember(
    val name: String,
    val initials: String,
    val role: DomainMemberRole
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
    val members: List<DomainMember>
)
