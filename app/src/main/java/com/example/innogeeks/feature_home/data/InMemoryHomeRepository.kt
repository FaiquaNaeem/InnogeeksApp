package com.example.innogeeks.feature_home.data

import com.example.innogeeks.feature_home.domain.HomeRepository
import com.example.innogeeks.feature_home.domain.model.Achievement
import com.example.innogeeks.feature_home.domain.model.ClubStats
import com.example.innogeeks.feature_home.domain.model.DomainPreview

// Guest-mode data source. The backend has no public endpoints, so guest content is local.
class InMemoryHomeRepository : HomeRepository {

    override suspend fun getClubStats(): Result<ClubStats> {
        return Result.success(
            ClubStats(
                totalMembers = 150,
                totalProjects = 45,
                totalDomains = 6,
                totalEvents = 24
            )
        )
    }

    override suspend fun getDomains(): Result<List<DomainPreview>> {
        return Result.success(
            listOf(
                DomainPreview(
                    id = "webd",
                    name = "Web Dev",
                    wheelLabel = "WEB D",
                    blurb = "Full-stack crews building the club's own platforms."
                ),
                DomainPreview(
                    id = "appd",
                    name = "App Dev",
                    wheelLabel = "APP D",
                    blurb = "Native & cross-platform builders shipping Android and iOS apps."
                ),
                DomainPreview(
                    id = "ml",
                    name = "Machine Learning",
                    wheelLabel = "ML",
                    blurb = "Model-training practitioners chasing leaderboard ranks."
                ),
                DomainPreview(
                    id = "arvr",
                    name = "AR / VR",
                    wheelLabel = "AR VR",
                    blurb = "Immersive tinkerers building spatial experiences with Unity & WebXR."
                ),
                DomainPreview(
                    id = "blockchain",
                    name = "Blockchain",
                    wheelLabel = "CHAIN",
                    blurb = "Smart contracts, chains, and Web3 tooling explorers."
                ),
                DomainPreview(
                    id = "iot",
                    name = "IoT",
                    wheelLabel = "IOT",
                    blurb = "Hardware and firmware hackers wiring sensors to the real world."
                )
            )
        )
    }

    override suspend fun getAchievements(): Result<List<Achievement>> {
        return Result.success(
            listOf(
                Achievement("a1", "🏆", "Finalist", "Smart India Hackathon"),
                Achievement("a2", "🚀", "Nominee", "NASA Space Apps — Global"),
                Achievement("a3", "🥈", "Top 50", "Flipkart GRiD 5.0"),
                Achievement("a4", "🛠️", "50+", "Projects Shipped"),
                Achievement("a5", "🎓", "40+", "Mentees Guided")
            )
        )
    }

    override suspend fun getTickerKeywords(): Result<List<List<String>>> {
        return Result.success(
            listOf(
                listOf("Technology", "Design", "Robotics", "Open Source"),
                listOf("Innovation", "Community", "Mentorship", "Research"),
                listOf("Hackathon", "Code", "Workshops", "Prototypes")
            )
        )
    }

    override suspend fun getCultureMoments(): Result<List<String>> {
        return Result.success(listOf("📡", "🤖", "🏆", "🎤", "🎉"))
    }
}
