package com.example.innogeeks.feature_domains.data

import com.example.innogeeks.feature_domains.domain.DomainsRepository
import com.example.innogeeks.feature_domains.domain.model.Domain
import com.example.innogeeks.feature_domains.domain.model.DomainMember
import com.example.innogeeks.feature_domains.domain.model.DomainMemberRole.COORDINATOR
import com.example.innogeeks.feature_domains.domain.model.DomainMemberRole.TEAM
import com.example.innogeeks.feature_domains.domain.model.DomainStat

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
                    emoji = "🌐",
                    accentIndex = 0,
                    stats = listOf(
                        DomainStat(18, "Members"),
                        DomainStat(12, "Projects"),
                        DomainStat(3, "Hackathon Wins")
                    ),
                    techStack = listOf("React", "Node.js", "Tailwind", "MongoDB", "TypeScript"),
                    projects = listOf("Innogeeks Website", "Event Portal", "Alumni Network"),
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
                    emoji = "📱",
                    accentIndex = 1,
                    stats = listOf(
                        DomainStat(14, "Members"),
                        DomainStat(9, "Apps Shipped"),
                        DomainStat(2, "Hackathon Wins")
                    ),
                    techStack = listOf("Kotlin", "Flutter", "Firebase", "Jetpack Compose"),
                    projects = listOf("Innogeeks App", "Campus Navigator", "Mess Menu Tracker"),
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
                    emoji = "🧠",
                    accentIndex = 2,
                    stats = listOf(
                        DomainStat(11, "Members"),
                        DomainStat(7, "Models Trained"),
                        DomainStat(4, "Kaggle Medals")
                    ),
                    techStack = listOf("Python", "TensorFlow", "PyTorch", "Scikit-learn", "Pandas"),
                    projects = listOf("Attendance Face-ID", "Crop Yield Predictor", "Chatbot Assistant"),
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
                    emoji = "🕶️",
                    accentIndex = 3,
                    stats = listOf(
                        DomainStat(9, "Members"),
                        DomainStat(5, "XR Demos"),
                        DomainStat(1, "Hackathon Win")
                    ),
                    techStack = listOf("Unity", "WebXR", "ARCore", "Blender"),
                    projects = listOf("Campus AR Tour", "VR Lab Simulator", "Holo Notice Board"),
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
                    emoji = "⛓️",
                    accentIndex = 4,
                    stats = listOf(
                        DomainStat(7, "Members"),
                        DomainStat(6, "Contracts Deployed"),
                        DomainStat(2, "Hackathon Wins")
                    ),
                    techStack = listOf("Solidity", "Ethereum", "Hardhat", "IPFS"),
                    projects = listOf("Certificate Verifier", "Campus Voting dApp", "NFT Ticketing"),
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
                    emoji = "📡",
                    accentIndex = 5,
                    stats = listOf(
                        DomainStat(8, "Members"),
                        DomainStat(10, "Devices Built"),
                        DomainStat(2, "Hackathon Wins")
                    ),
                    techStack = listOf("Arduino", "Raspberry Pi", "ESP32", "MQTT"),
                    projects = listOf(
                        "Smart Attendance Node",
                        "Campus Air Quality Sensor",
                        "Auto Irrigation Rig"
                    ),
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
