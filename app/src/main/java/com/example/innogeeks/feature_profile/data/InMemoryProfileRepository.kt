package com.example.innogeeks.feature_profile.data

import com.example.innogeeks.feature_profile.domain.ProfileRepository
import com.example.innogeeks.feature_profile.domain.model.DetailEntry
import com.example.innogeeks.feature_profile.domain.model.DomainBadge
import com.example.innogeeks.feature_profile.domain.model.StudentProfile

// Hardcoded — the API contract exposes no profile endpoint.
class InMemoryProfileRepository : ProfileRepository {

    override suspend fun getProfile(): Result<StudentProfile> {
        return Result.success(
            StudentProfile(
                name = "Ayush",
                initials = "AY",
                subtitle = "ECE-A · 5th Semester · KIET Group of Institutions",
                roleChip = "Innogeeks Core Team",
                domainCount = 2,
                eventCount = 6,
                achievementCount = 4,
                academicDetails = listOf(
                    DetailEntry("Enrollment No.", "202401100700051"),
                    DetailEntry("Branch", "Electronics & Communication Engg."),
                    DetailEntry("Section", "ECE-A"),
                    DetailEntry("Semester", "5th"),
                    DetailEntry("CGPA", "8.0")
                ),
                clubDetails = listOf(
                    DetailEntry("Role", "Core Team · Innogeeks"),
                    DetailEntry("Research", "Pressure-measurement device for orthotic design")
                ),
                domainBadges = listOf(
                    DomainBadge("Web Dev", 0),
                    DomainBadge("AR / VR", 3)
                )
            )
        )
    }
}
