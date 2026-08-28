package com.example.innogeeks.feature_resources.data

import com.example.innogeeks.feature_resources.domain.ResourcesRepository
import com.example.innogeeks.feature_resources.domain.model.ResourceCategory
import com.example.innogeeks.feature_resources.domain.model.ResourceItem
import com.example.innogeeks.feature_resources.domain.model.ResourceType

// Registered-tab data source. No resources endpoint exists yet.
class InMemoryResourcesRepository : ResourcesRepository {

    override suspend fun getResourceCategories(): Result<List<ResourceCategory>> {
        return Result.success(
            listOf(
                ResourceCategory(
                    id = "recruitment-prep",
                    title = "Recruitment Prep",
                    description = "What to expect before your test and interview.",
                    items = listOf(
                        ResourceItem(
                            id = "recruitment-prep-guide",
                            title = "Innogeeks Recruitment Guide",
                            type = ResourceType.ARTICLE,
                            url = "https://innogeeks.tech/recruitment-guide"
                        ),
                        ResourceItem(
                            id = "recruitment-faq",
                            title = "Recruitment FAQ",
                            type = ResourceType.ARTICLE,
                            url = "https://innogeeks.tech/recruitment-faq"
                        )
                    )
                ),
                ResourceCategory(
                    id = "club-handbook",
                    title = "Club Handbook",
                    description = "How Innogeeks runs — domains, events, and expectations.",
                    items = listOf(
                        ResourceItem(
                            id = "handbook-pdf",
                            title = "Member Handbook (PDF)",
                            type = ResourceType.PDF,
                            url = "https://innogeeks.tech/handbook.pdf"
                        ),
                        ResourceItem(
                            id = "discord-invite",
                            title = "Join the Discord",
                            type = ResourceType.LINK,
                            url = "https://discord.gg/innogeeks"
                        )
                    )
                ),
                ResourceCategory(
                    id = "getting-started",
                    title = "Getting Started",
                    description = "Pick a domain and start building before you're even a member.",
                    items = listOf(
                        ResourceItem(
                            id = "domain-overview-video",
                            title = "Domain Overview Walkthrough",
                            type = ResourceType.VIDEO,
                            url = "https://innogeeks.tech/domains-walkthrough"
                        ),
                        ResourceItem(
                            id = "first-project-guide",
                            title = "Your First Project",
                            type = ResourceType.ARTICLE,
                            url = "https://innogeeks.tech/first-project"
                        )
                    )
                )
            )
        )
    }
}
