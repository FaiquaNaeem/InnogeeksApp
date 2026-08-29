package com.example.innogeeks.feature_events.data

import com.example.innogeeks.feature_events.domain.EventsRepository
import com.example.innogeeks.feature_events.domain.model.ClubEvent
import kotlinx.datetime.LocalDate

// Guest-mode data source. No events endpoint exists yet — full dates (and, for recurring
// events, the next-occurrence date) will come from the backend once it does.
class InMemoryEventsRepository : EventsRepository {

    override suspend fun getEvents(): Result<List<ClubEvent>> {
        return Result.success(
            listOf(
                ClubEvent(
                    id = "e1",
                    title = "Innogeeks Open Mic + Demo Day",
                    date = LocalDate(2026, 10, 5),
                    timeAndPlace = "5:30 PM · Amphitheatre",
                    description = "Members showcase side-projects in 5-minute lightning demos, followed by an open mic."
                ),
                ClubEvent(
                    id = "e2",
                    title = "AI/ML Bootcamp — Session 2",
                    date = LocalDate(2026, 9, 22),
                    timeAndPlace = "3:00 PM · Seminar Hall",
                    description = "Hands-on session on model evaluation and hyperparameter tuning, continuing from Session 1."
                ),
                ClubEvent(
                    id = "e3",
                    title = "Web Dev Weekly Standup",
                    date = LocalDate(2026, 9, 1),
                    timeAndPlace = "6:00 PM · Innogeeks Lab",
                    description = "Weekly sync for the Web Dev domain — progress updates, blockers, and pairing for the week ahead.",
                    isRecurring = true,
                    cadence = "Every Tuesday, 6 PM"
                ),
                ClubEvent(
                    id = "e4",
                    title = "Hack The Campus 3.0",
                    date = LocalDate(2026, 8, 14),
                    timeAndPlace = "10:00 AM · Main Auditorium",
                    description = "A 24-hour campus-wide hackathon open to all branches. Teams of up to 4, problem statements released on the day.",
                    attendees = 210
                ),
                ClubEvent(
                    id = "e5",
                    title = "Innogeeks Annual Meet 2026",
                    date = LocalDate(2026, 3, 22),
                    description = "The club's biggest gathering of the year — recap of the year's wins, domain showcases, and the annual awards.",
                    attendees = 180
                ),
                ClubEvent(
                    id = "e6",
                    title = "Smart India Hackathon — Internal Round",
                    date = LocalDate(2026, 2, 10),
                    description = "Internal selection round for SIH, with 24 teams pitching problem-statement solutions to a panel of faculty judges.",
                    attendees = 96
                ),
                ClubEvent(
                    id = "e7",
                    title = "Web Dev Workshop: React Basics",
                    date = LocalDate(2026, 1, 18),
                    description = "A beginner-friendly workshop covering components, hooks, and state — most attendees shipped a mini project by the end.",
                    attendees = 64
                )
            )
        )
    }
}
