package com.example.innogeeks.feature_resources.data

import com.example.innogeeks.feature_resources.domain.ResourcesRepository
import com.example.innogeeks.feature_resources.domain.model.ResourceItem
import com.example.innogeeks.feature_resources.domain.model.ResourceType.GITHUB
import com.example.innogeeks.feature_resources.domain.model.ResourceType.LINK
import com.example.innogeeks.feature_resources.domain.model.ResourceType.NOTES
import com.example.innogeeks.feature_resources.domain.model.ResourceType.PDF
import com.example.innogeeks.feature_resources.domain.model.ResourceType.VIDEO

// Registered-tab data source. No resources endpoint exists yet — domainId here matches
// Domain.id from DomainsRepository, so the picker screen can join the two lists by id.
class InMemoryResourcesRepository : ResourcesRepository {

    override suspend fun getResources(): Result<List<ResourceItem>> {
        return Result.success(
            listOf(
                ResourceItem(
                    id = "w1", domainId = "webd", type = LINK, emoji = "🌐",
                    title = "The Odin Project",
                    description = "Full-stack web dev curriculum — HTML, CSS, JS, Node, React. Free and structured like a real bootcamp.",
                    author = "Ritesh Kumar", date = "Aug 2026", level = "Beginner",
                    url = "https://www.theodinproject.com"
                ),
                ResourceItem(
                    id = "w2", domainId = "webd", type = PDF, emoji = "📄",
                    title = "CSS Grid & Flexbox Cheatsheet",
                    description = "Compact visual reference card for CSS layout — every property, every value, with diagrams.",
                    author = "Neha Singh", date = "Jul 2026", level = "Beginner",
                    url = "https://innogeeks.tech/css-cheatsheet.pdf"
                ),
                ResourceItem(
                    id = "w3", domainId = "webd", type = VIDEO, emoji = "▶️",
                    title = "JS Event Loop — Visualised",
                    description = "Philip Roberts's JSConf talk. Explains the call stack, task queue, and microtasks in 26 minutes.",
                    author = "Aditya Sharma", date = "Jun 2026", level = "Intermediate",
                    url = "https://youtu.be/8aGhZQkoFbQ"
                ),
                ResourceItem(
                    id = "w4", domainId = "webd", type = GITHUB, emoji = "🐙",
                    title = "Innogeeks Web Starter Kit",
                    description = "Boilerplate repo: Vite + Tailwind + ESLint + Prettier + git hooks. Clone and build.",
                    author = "Priya Verma", date = "Aug 2026", level = "Intermediate",
                    url = "https://github.com/innogeeks/web-starter"
                ),

                ResourceItem(
                    id = "a1", domainId = "appd", type = LINK, emoji = "🔗",
                    title = "Android Developers — Compose",
                    description = "Official Google docs for Jetpack Compose. Best starting point for UI, state and navigation.",
                    author = "Faiq", date = "Aug 2026", level = "Beginner",
                    url = "https://developer.android.com/jetpack/compose"
                ),
                ResourceItem(
                    id = "a2", domainId = "appd", type = PDF, emoji = "📄",
                    title = "MVI Architecture Guide",
                    description = "Internal PDF walking through Model-View-Intent, and why we prefer it over MVVM, with samples from this app.",
                    author = "Faiq", date = "Aug 2026", level = "Intermediate",
                    url = "https://innogeeks.tech/mvi-guide.pdf"
                ),
                ResourceItem(
                    id = "a3", domainId = "appd", type = VIDEO, emoji = "▶️",
                    title = "Haze Glassmorphism in Compose",
                    description = "Full tutorial on using the Haze 2.0 library to build frosted-glass surfaces in a production app.",
                    author = "Ankit Rao", date = "Jul 2026", level = "Intermediate",
                    url = "https://innogeeks.tech/haze-tutorial"
                ),
                ResourceItem(
                    id = "a4", domainId = "appd", type = GITHUB, emoji = "🐙",
                    title = "Innogeeks Android App",
                    description = "The app you're building. Reference it for architecture conventions, Koin DI and MVI screen structure.",
                    author = "Faiq", date = "Aug 2026", level = "Advanced",
                    url = "https://github.com/innogeeks/android-app"
                ),

                ResourceItem(
                    id = "m1", domainId = "ml", type = LINK, emoji = "🔗",
                    title = "fast.ai — Practical Deep Learning",
                    description = "Top-down practical course, completely free. Covers CNNs, NLP, tabular data and recommendation systems.",
                    author = "Siddharth Jain", date = "Jul 2026", level = "Beginner",
                    url = "https://course.fast.ai"
                ),
                ResourceItem(
                    id = "m2", domainId = "ml", type = PDF, emoji = "📄",
                    title = "Linear Algebra Refresher for ML",
                    description = "30-page PDF covering vectors, matrices, eigenvalues and the intuition behind them.",
                    author = "Priyanka Das", date = "Apr 2026", level = "Beginner",
                    url = "https://innogeeks.tech/linear-algebra.pdf"
                ),
                ResourceItem(
                    id = "m3", domainId = "ml", type = GITHUB, emoji = "🐙",
                    title = "ML Projects — Innogeeks",
                    description = "Shared notebook collection: image classifier, sentiment analyser, recommendation engine.",
                    author = "Siddharth Jain", date = "Aug 2026", level = "Intermediate",
                    url = "https://github.com/innogeeks/ml-projects"
                ),

                ResourceItem(
                    id = "ar1", domainId = "arvr", type = LINK, emoji = "🔗",
                    title = "Unity AR Foundation Docs",
                    description = "Official Unity docs for AR Foundation — plane detection, raycasting, image tracking, light estimation.",
                    author = "Kiran Gupta", date = "Jun 2026", level = "Intermediate",
                    url = "https://docs.unity3d.com/Packages/com.unity.xr.arfoundation@latest"
                ),
                ResourceItem(
                    id = "ar2", domainId = "arvr", type = NOTES, emoji = "📝",
                    title = "VR UX Principles",
                    description = "Internal notes on comfort zones, FOV constraints, locomotion options, and avoiding simulator sickness.",
                    author = "Kiran Gupta", date = "May 2026", level = "Intermediate",
                    url = "https://innogeeks.tech/vr-ux-notes"
                ),

                ResourceItem(
                    id = "b1", domainId = "blockchain", type = LINK, emoji = "🔗",
                    title = "CryptoZombies",
                    description = "Gamified Solidity course — build a zombie game while learning smart contracts.",
                    author = "Harsh Agarwal", date = "Jun 2026", level = "Beginner",
                    url = "https://cryptozombies.io"
                ),
                ResourceItem(
                    id = "b2", domainId = "blockchain", type = GITHUB, emoji = "🐙",
                    title = "Smart Contract Samples",
                    description = "ERC-20, ERC-721, and voting contract templates tested with Hardhat.",
                    author = "Sneha Patel", date = "Jul 2026", level = "Intermediate",
                    url = "https://github.com/innogeeks/contract-samples"
                ),

                ResourceItem(
                    id = "i1", domainId = "iot", type = LINK, emoji = "🔗",
                    title = "Arduino Project Hub",
                    description = "Official curated library of beginner-to-advanced IoT builds, each with full code and schematics.",
                    author = "Rahul Bajpai", date = "Jul 2026", level = "Beginner",
                    url = "https://projecthub.arduino.cc"
                ),
                ResourceItem(
                    id = "i2", domainId = "iot", type = NOTES, emoji = "📝",
                    title = "ESP32 vs Raspberry Pi",
                    description = "Internal comparison — power draw, connectivity, programmability and cost for club project scenarios.",
                    author = "Divya Tiwari", date = "Jun 2026", level = "Beginner",
                    url = "https://innogeeks.tech/esp32-vs-pi"
                )
            )
        )
    }
}
