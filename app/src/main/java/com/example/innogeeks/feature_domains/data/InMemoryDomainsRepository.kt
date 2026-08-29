package com.example.innogeeks.feature_domains.data

import com.example.innogeeks.feature_domains.domain.DomainsRepository
import com.example.innogeeks.feature_domains.domain.model.Domain
import com.example.innogeeks.feature_domains.domain.model.DomainMember
import com.example.innogeeks.feature_domains.domain.model.DomainMemberRole.COORDINATOR
import com.example.innogeeks.feature_domains.domain.model.DomainMemberRole.TEAM

// Guest-mode data source. No domains endpoint exists yet — swap this for a Ktor-backed
// implementation once /domains ships; DomainsRepository is the contract callers already code against.
class InMemoryDomainsRepository : DomainsRepository {

    override suspend fun getDomains(): Result<List<Domain>> {
        return Result.success(
            listOf(
                Domain(
                    id = "webd",
                    name = "Web Dev",
                    tagline = "React, Node & everything between",
                    description = "Web Dev builds and maintains all of Innogeeks' web-facing tools, from the club site to event portals.",
                    emoji = "🌐",
                    accentIndex = 0,
                    memberCount = 18,
                    techStack = listOf("React", "Node.js", "Tailwind", "MongoDB", "TypeScript"),
                    members = listOf(
                        DomainMember("Priya Sharma", "PS", COORDINATOR),
                        DomainMember("Rahul Deshmukh", "RD", COORDINATOR),
                        DomainMember("Ananya Iyer", "AI", TEAM),
                        DomainMember("Yash Malhotra", "YM", TEAM),
                        DomainMember("Sneha Pillai", "SP", TEAM)
                    )
                ),
                Domain(
                    id = "appd",
                    name = "App Dev",
                    tagline = "Native & cross-platform builders",
                    description = "App Dev designs and ships the club's native and cross-platform mobile apps, end to end.",
                    emoji = "📱",
                    accentIndex = 1,
                    memberCount = 14,
                    techStack = listOf("Kotlin", "Flutter", "Firebase", "Jetpack Compose"),
                    members = listOf(
                        DomainMember("Rohan Verma", "RV", COORDINATOR),
                        DomainMember("Kavya Reddy", "KR", COORDINATOR),
                        DomainMember("Arjun Nanda", "AN", TEAM),
                        DomainMember("Divya Krishnan", "DK", TEAM)
                    )
                ),
                Domain(
                    id = "ml",
                    name = "Machine Learning",
                    tagline = "Models, data & leaderboard chasing",
                    description = "Machine Learning explores applied ML and data science, from model training to real-world deployment.",
                    emoji = "🧠",
                    accentIndex = 2,
                    memberCount = 11,
                    techStack = listOf("Python", "TensorFlow", "PyTorch", "Scikit-learn", "Pandas"),
                    members = listOf(
                        DomainMember("Ananya Gupta", "AG", COORDINATOR),
                        DomainMember("Vikram Rao", "VR", COORDINATOR),
                        DomainMember("Nisha Bhat", "NB", TEAM),
                        DomainMember("Aditya Menon", "AM", TEAM),
                        DomainMember("Pooja Chawla", "PC", TEAM)
                    )
                ),
                Domain(
                    id = "arvr",
                    name = "AR / VR",
                    tagline = "Spatial experiences & immersive tech",
                    description = "AR / VR prototypes spatial and immersive experiences, working across headsets, mobile AR, and the web.",
                    emoji = "🕶️",
                    accentIndex = 3,
                    memberCount = 9,
                    techStack = listOf("Unity", "WebXR", "ARCore", "Blender"),
                    members = listOf(
                        DomainMember("Karan Mehta", "KM", COORDINATOR),
                        DomainMember("Ritika Joshi", "RJ", COORDINATOR),
                        DomainMember("Sameer Khan", "SK", TEAM)
                    )
                ),
                Domain(
                    id = "blockchain",
                    name = "Blockchain",
                    tagline = "Smart contracts & Web3 tooling",
                    description = "Blockchain builds smart contracts and Web3 tooling, covering everything from dApps to on-chain infrastructure.",
                    emoji = "⛓️",
                    accentIndex = 4,
                    memberCount = 7,
                    techStack = listOf("Solidity", "Ethereum", "Hardhat", "IPFS"),
                    members = listOf(
                        DomainMember("Ishaan Kapoor", "IK", COORDINATOR),
                        DomainMember("Tanvi Agarwal", "TA", TEAM),
                        DomainMember("Devansh Oberoi", "DO", TEAM)
                    )
                ),
                Domain(
                    id = "iot",
                    name = "IoT",
                    tagline = "Sensors, boards & the physical world",
                    description = "IoT connects sensors, boards, and the physical world, building hardware-backed projects from prototype to deployment.",
                    emoji = "📡",
                    accentIndex = 5,
                    memberCount = 8,
                    techStack = listOf("Arduino", "Raspberry Pi", "ESP32", "MQTT"),
                    members = listOf(
                        DomainMember("Meera Nair", "MN", COORDINATOR),
                        DomainMember("Farhan Sheikh", "FS", COORDINATOR),
                        DomainMember("Ojas Kulkarni", "OK", TEAM)
                    )
                )
            )
        )
    }
}
