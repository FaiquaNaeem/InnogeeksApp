package com.example.innogeeks.feature_domains.data

import com.example.innogeeks.feature_domains.domain.DomainsRepository
import com.example.innogeeks.feature_domains.domain.model.Domain
import com.example.innogeeks.feature_domains.domain.model.DomainLead
import com.example.innogeeks.feature_domains.domain.model.DomainStat

// Guest-mode data source. No domains endpoint exists yet.
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
                    lead = DomainLead("Priya Sharma", "Domain Lead · Web Dev", "PS")
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
                    lead = DomainLead("Rohan Verma", "Domain Lead · App Dev", "RV")
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
                    lead = DomainLead("Ananya Gupta", "Domain Lead · ML", "AG")
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
                    lead = DomainLead("Karan Mehta", "Domain Lead · AR/VR", "KM")
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
                    lead = DomainLead("Ishaan Kapoor", "Domain Lead · Blockchain", "IK")
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
                    lead = DomainLead("Meera Nair", "Domain Lead · IoT", "MN")
                )
            )
        )
    }
}
