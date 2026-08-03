package com.example.innogeeks.feature_events.data

import com.example.innogeeks.feature_events.domain.EventsRepository
import com.example.innogeeks.feature_events.domain.model.ClubEvent

// Guest-mode data source. No events endpoint exists yet.
class InMemoryEventsRepository : EventsRepository {

    override suspend fun getEvents(): Result<List<ClubEvent>> {
        return Result.success(
            listOf(
                ClubEvent(
                    id = "u1",
                    day = "14",
                    month = "AUG",
                    title = "Hack The Campus 3.0",
                    isUpcoming = true,
                    timeAndPlace = "10:00 AM · Main Auditorium",
                    description = "A 24-hour campus-wide hackathon open to all branches. Teams of up to 4, problem statements released on the day."
                ),
                ClubEvent(
                    id = "u2",
                    day = "22",
                    month = "AUG",
                    title = "AI/ML Bootcamp — Session 2",
                    isUpcoming = true,
                    timeAndPlace = "3:00 PM · Seminar Hall",
                    description = "Hands-on session on model evaluation and hyperparameter tuning, continuing from Session 1."
                ),
                ClubEvent(
                    id = "u3",
                    day = "05",
                    month = "SEP",
                    title = "Innogeeks Open Mic + Demo Day",
                    isUpcoming = true,
                    timeAndPlace = "5:30 PM · Amphitheatre",
                    description = "Members showcase side-projects in 5-minute lightning demos, followed by an open mic."
                ),
                ClubEvent(
                    id = "p1",
                    day = "22",
                    month = "MAR",
                    title = "Innogeeks Annual Meet 2026",
                    isUpcoming = false,
                    attendees = 180,
                    recap = "The club's biggest gathering of the year — recap of the year's wins, domain showcases, and the annual awards."
                ),
                ClubEvent(
                    id = "p2",
                    day = "10",
                    month = "FEB",
                    title = "Smart India Hackathon — Internal Round",
                    isUpcoming = false,
                    attendees = 96,
                    recap = "Internal selection round for SIH, with 24 teams pitching problem-statement solutions to a panel of faculty judges."
                ),
                ClubEvent(
                    id = "p3",
                    day = "18",
                    month = "JAN",
                    title = "Web Dev Workshop: React Basics",
                    isUpcoming = false,
                    attendees = 64,
                    recap = "A beginner-friendly workshop covering components, hooks, and state — most attendees shipped a mini project by the end."
                )
            )
        )
    }
}
