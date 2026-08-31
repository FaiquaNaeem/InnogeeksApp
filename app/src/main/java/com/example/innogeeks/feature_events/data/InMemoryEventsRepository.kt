package com.example.innogeeks.feature_events.data

import com.example.innogeeks.R
import com.example.innogeeks.feature_events.domain.EventsRepository
import com.example.innogeeks.feature_events.domain.model.ClubEvent
import kotlinx.datetime.LocalDate

// Guest-mode data source. No events endpoint exists yet, so the club's real event
// history (transcribed from docs/INNOGEEKS_EVENTS.md) is hardcoded here.
class InMemoryEventsRepository : EventsRepository {

    override suspend fun getEvents(): Result<List<ClubEvent>> {
        return Result.success(
            listOf(
                ClubEvent(
                    id = "e1",
                    title = "Weekend Hour – GitHub",
                    date = LocalDate(2021, 6, 27),
                    description = "A successful session of Weekend Hour - GitHub was conducted by Team Innogeeks, focusing on introducing participants to the fundamentals of version control and collaborative development. The session covered key topics including Git basics, repository management, branching, committing changes, and working with GitHub for real-world project collaboration.",
                    // No photo of this session survives — reusing a later Git & GitHub session photo.
                    cardImageRes = R.drawable.event_git_feb2025_a
                ),
                ClubEvent(
                    id = "e2",
                    title = "Weekend Hour – Linux",
                    date = LocalDate(2021, 7, 2),
                    description = "As a part of our Weekend Hour series, an informative session on Linux was held. It aimed at introducing participants to the fundamentals of the Linux operating system and its practical applications, covering essential topics such as Linux commands, file system navigation, terminal usage, and basic shell operations.",
                    // No photo of this session survives — reusing the Linux Bootcamp photo.
                    cardImageRes = R.drawable.event_linux_bootcamp_2022
                ),
                ClubEvent(
                    id = "e3",
                    title = "CoderSpree 1.0",
                    date = LocalDate(2021, 10, 1),
                    description = "</CODERSPREE> provided 2nd and 3rd-year students with an excellent platform to immerse themselves in the Competitive Coding Arena, fostering a strong competitive programming environment irrespective of programming language. Outcome: 1000+ submissions, 100+ contributors. Languages: C++ 69.1%, Java 24.0%, Python 2.8%, C 4.1%.",
                    attendees = 100,
                    eventLink = "https://www.instagram.com/p/CWfhC-0tZai/",
                    cardImageRes = R.drawable.event_coderspree1
                ),
                ClubEvent(
                    id = "e4",
                    title = "Placement Module – Mock Interviews",
                    date = LocalDate(2021, 10, 18),
                    description = "In a collaborative effort to foster peer-to-peer learning, Mock Interviews were conducted by experienced seniors of Innogeeks for the third-year students to help them get a better understanding of real interview scenarios. This peer-driven activity aimed to boost confidence, improve communication skills, and provide valuable feedback.",
                    eventLink = "https://www.instagram.com/p/CW7losZNPbN/",
                    cardImageRes = R.drawable.event_mock_interviews_2021
                ),
                ClubEvent(
                    id = "e8",
                    title = "InnoHacks – Hack N' Innovate",
                    date = LocalDate(2022, 5, 15),
                    timeAndPlace = "Applications opened 14 April 2022",
                    description = "InnoHacks 2022 marked the first-ever hackathon organized by Team Innogeeks, providing a platform where creativity met technology. Held in May 2022, it became one of the largest collegiate hackathons in Delhi-NCR, with 100k+ reach and a prize pool of over ₹1.5 lakhs. Jury panel: Raj Vikramaditya (Striver), Arsh Goyal, Akshay Saini. Speaker session by Sandeep Jain (Founder – GfG). Platinum sponsors: GitHub, GeeksforGeeks. Gold sponsors: Coding Minutes, Newton School, Jain Bakers, auth0. Prize pool ₹1.50 lakhs: 1st ₹50,000, 2nd ₹25,000, 3rd ₹15,000 + swags.",
                    eventLink = "https://www.instagram.com/p/Cd-gb36BxxV/",
                    cardImageRes = R.drawable.event_innohacks_2022
                ),
                ClubEvent(
                    id = "e9",
                    title = "Git & GitHub Bootcamp",
                    date = LocalDate(2022, 9, 15),
                    description = "Team Innogeeks conducted an engaging and informative Git & GitHub Bootcamp, aimed at helping students get hands-on experience with version control and collaborative development, covering Git fundamentals, GitHub workflows, and practical commands to manage real-world projects.",
                    // No photo of this bootcamp survives — reusing a later Git & GitHub session photo.
                    cardImageRes = R.drawable.event_git_feb2025_b_a
                ),
                ClubEvent(
                    id = "e10",
                    title = "Linux Bootcamp",
                    date = LocalDate(2022, 11, 11),
                    description = "Hands-on Linux was a 1-day bootcamp to get you started with Linux, covering key topics like using the Linux command line, exploring essential tools, and understanding what makes Linux such a powerful and versatile operating system.",
                    eventLink = "https://www.instagram.com/p/CkxI0utrBuA/",
                    cardImageRes = R.drawable.event_linux_bootcamp_2022
                ),
                ClubEvent(
                    id = "e11",
                    title = "Innogeeks Winter of Code 1.0",
                    date = LocalDate(2023, 2, 1),
                    timeAndPlace = "Ran through 28 February",
                    description = "Innogeeks Winter of Code – IWOC'23 stood up as a unique, month-long open-source journey that brought together tech enthusiasts to learn, contribute, and grow, promoting open-source culture within the college. Speakers: Unnati Chhabra, Kanishk Pachauri, Saksham Saini, Vanshika Garg, Shashank Srivastava, Tushar Gupta, Yash Garg, Shreya Prasad. Prize pool ₹10K+: 1st ₹5,000, 2nd ₹3,000, 3rd ₹2,000. Winners: #1 Suryansh Prajapati, #2 Shivam Kumar, #3 Ayush Agarwal, #4 Ayush Chauhan, #5 Dhruv Porwal.",
                    eventLink = "https://www.instagram.com/p/CnqqsIMLm74/",
                    cardImageRes = R.drawable.event_iwoc1
                ),
                ClubEvent(
                    id = "e12",
                    title = "InnoHacks 2.0 – Hack & Innovate",
                    date = LocalDate(2023, 4, 22),
                    description = "InnoHacks 2.0, the second edition of Innogeeks' flagship offline hackathon, was held at KIET Group of Institutions, Delhi-NCR, organized in collaboration with TBI KIET and powered by HDFC Bank. With 200K+ reach and a prize pool of over ₹11.25 lakhs, the hackathon brought together talented minds from across India. Speakers & judges: Harsh Sharma, Kushal Vijay, Khushboo Verma, Love Babbar. Title sponsor: HDFC Bank. Platinum sponsors: MySphere, 5ire.",
                    eventLink = "https://www.instagram.com/p/CqA_ONISFzu/",
                    cardImageRes = R.drawable.event_innohacks2
                ),
                ClubEvent(
                    id = "e15",
                    title = "CoderSpree 3.0",
                    date = LocalDate(2023, 10, 4),
                    timeAndPlace = "Ran through 31 October",
                    description = "CoderSpree 3.0 was an exciting month-long coding sprint that united problem-solving enthusiasts, whether they were just starting out or seasoned pros. Participants gained entry to a special Innogeeks Discord server, took part in mentorship sessions led by experts, and battled it out on the leaderboard for exclusive CoderSpree goodies.",
                    eventLink = "https://www.instagram.com/p/CyC7LjorwV5/",
                    cardImageRes = R.drawable.event_coderspree3
                ),
                ClubEvent(
                    id = "e16",
                    title = "Innogeeks Winter of Code 2.0",
                    date = LocalDate(2024, 1, 1),
                    description = "IWOC 2.0 was a month-long celebration of open source that spanned the entire country, building on the success of its earlier edition. Community partners: GDSC GCET, GDSC ABESEC, GDSC ABESIT, LOOP Galgotia's Coding Club, IOSC Club Bhrati Vidyapeeth. Prizes: 1st ₹5,000, 2nd ₹3,000, 3rd ₹2,000 + swags for top 10 contributors.",
                    eventLink = "https://www.instagram.com/p/C1_cdPjLsSQ/",
                    // No photo of this edition survives — reusing the IWOC 1.0 photo.
                    cardImageRes = R.drawable.event_iwoc1
                ),
                ClubEvent(
                    id = "e17",
                    title = "Git & GitHub Workshop",
                    date = LocalDate(2024, 1, 9),
                    description = "The Git & GitHub Workshop provided participants with a hands-on introduction to version control and open-source collaboration, covering Git fundamentals, GitHub workflows, repository management, branching, and contributing to projects.",
                    eventLink = "https://www.linkedin.com/posts/innogeeks_relive-the-excitement-of-iwoc-20s-git-activity-7151811362187284480-rPnh",
                    // No photo of this workshop survives — reusing a later Git & GitHub session photo.
                    cardImageRes = R.drawable.event_git_feb2025_b_b
                ),
                ClubEvent(
                    id = "e18",
                    title = "Speaker Session (IWOC 2.0)",
                    date = LocalDate(2024, 2, 2),
                    timeAndPlace = "KSOP Hall",
                    description = "IWOC 2.0 brought together some of the brightest minds in open-source and software development for an insightful speaker session. Lineup: Piyush Garg – Software Engineer at Emitrr & YouTuber with 80K+ subscribers; Ayush Dubey – GSOC 2023 achiever & ASE Intern at Contentstack; Anvansh Singh – SDE Intern at Samagra, Captain at Djangonaut'24.",
                    eventLink = "https://www.instagram.com/p/C24ctMtywFf/",
                    cardImageRes = R.drawable.event_iwoc2_speaker
                ),
                ClubEvent(
                    id = "e19",
                    title = "Innohacks 3.0",
                    date = LocalDate(2024, 3, 11),
                    description = "Innohacks 3.0 was a thrilling national-level hackathon organized by Innogeeks, bringing together the brightest minds to code, create, and conquer. Title sponsor: MeitY. Hosting partner: Devfolio. Total prize pool ₹15 lakhs: winner ₹70,000, runner-up ₹35,000, third place ₹20,000. Gold sponsors: Inovact, Xerocodee, Polygon, ETHIndia. Platinum partner: GitHub. Mentors: Nikkhil Gehlot, Yuvraj Kachhawaha, Akhil Gupta, Ayush Kumar, Arya Soni. Special workshop 'Hack Smart with GitHub Copilot' by Ayush Kumar, GitHub Campus Expert.",
                    // No photo of this edition survives — reusing the Innohacks 2.0 photo.
                    cardImageRes = R.drawable.event_innohacks2
                ),
                ClubEvent(
                    id = "e20",
                    title = "Speaker Session (Innohacks 3.0)",
                    date = LocalDate(2024, 4, 21),
                    description = "Innohacks 3.0 featured an extraordinary lineup of renowned speakers and elite judges. Nishant Chahar – YouTube influencer with 390K+ subscribers, Ex-SDE @Microsoft. Kshitiz Miglani – Co-founder at Devsnest, Ex-Amazon, Ex-Paytm, Ex-Samsung. Tanya Rajhans – Member of Technical Staff at Devrev, Ex-Google, specialist in Docker, Kubernetes, Terraform, AWS.",
                    // No photo of this session survives — reusing the original Innohacks 2022 photo.
                    cardImageRes = R.drawable.event_innohacks_2022
                ),
                ClubEvent(
                    id = "e21",
                    title = "Oraichain Season of Docs",
                    date = LocalDate(2024, 5, 11),
                    description = "Oraichain Season of Docs (#OSoD) was an innovative program launched in collaboration with Innogeeks, designed to advance technical writing and project documentation in decentralized AI and blockchain technology, bringing together writers and developers within the Oraichain Ecosystem.",
                    eventLink = "https://www.instagram.com/p/C61GnRByp86/",
                    cardImageRes = R.drawable.event_oraichain
                ),
                ClubEvent(
                    id = "e23",
                    title = "Innogeeks Winter of Code 3.0 (IWOC) 2024-25",
                    date = LocalDate(2025, 1, 13),
                    description = "IWOC 3.0 is an annual event that introduces participants to the world of open-source development, providing a platform for developers from beginners to experts to collaborate on impactful projects. Prize pool: 1st ₹5,000, 2nd ₹3,000, 3rd ₹2,000. Winners: 1st Saksham Jain, 2nd Yash S. Pandav, 3rd Arka Basak. Speaker session by Pranav Kumar.",
                    eventLink = "https://iwoc3.devfolio.co/",
                    cardImageRes = R.drawable.event_iwoc3
                ),
                ClubEvent(
                    id = "e24",
                    title = "Git and GitHub Session",
                    date = LocalDate(2025, 2, 8),
                    description = "Innogeeks conducted an insightful session on Git and GitHub, introducing participants to the fundamentals of version control and collaborative development — tracking changes, managing repositories, hosting, and contributing to open-source projects.",
                    eventLink = "https://www.instagram.com/p/DFt_pNEP2CT/",
                    cardImageRes = R.drawable.event_git_feb2025_a
                ),
                ClubEvent(
                    id = "e25",
                    title = "Speaker Session",
                    date = LocalDate(2025, 2, 24),
                    description = "The IWOC 3.0 Speaker Session was a remarkable event that brought together tech enthusiasts and innovators. Led by Pranav Kumar, the session delved into the world of Open Source, offering deep insights and expert advice on thriving in the dynamic tech industry. Hosted by KIET Ghaziabad.",
                    eventLink = "https://www.instagram.com/p/DGhjoVwK11L/",
                    cardImageRes = R.drawable.event_iwoc3_speaker
                ),
                ClubEvent(
                    id = "e26",
                    title = "InnoForge",
                    date = LocalDate(2025, 6, 11),
                    timeAndPlace = "Ran through 28 August",
                    description = "InnoForge was a transformative initiative within the Innogeeks ecosystem, aimed at nurturing creativity, hands-on learning and peer collaboration among first-year students, conducted in a two-phase format: an online preliminary round for project proposals followed by an offline final round with live prototype presentations. Winning teams: Winner (₹2,000) Code4Cause, First Runner-up (₹1,000) GitOxide, Second Runner-up (₹500) CodeYatra.",
                    eventLink = "https://www.instagram.com/p/DOTkmIhjzXX/",
                    cardImageRes = R.drawable.event_innoforge_a,
                    galleryImageRes = listOf(R.drawable.event_innoforge_a, R.drawable.event_innoforge_b)
                ),
                ClubEvent(
                    id = "e27",
                    title = "NASA Space Apps Challenge 2025 | Ghaziabad Edition",
                    date = LocalDate(2025, 9, 27),
                    description = "NASA Space Apps Challenge | Ghaziabad Edition is a globally recognized innovation hackathon hosted at KIET Group of Institutions, focused on solving real-world challenges using NASA's open data. The event brought together 150+ innovators across 35+ teams from multiple cities. Mentors: Anvansh Singh, Vishesh Kumar Singh, Sambhrant Tiwari, Neha Maurya, Varun Agarwal. Speakers: Rohit Negi (Uber alumnus & founder of Coder Army), Aditya Tandon (Ola alumnus & Coder Army), Rajesh Hadiya (former Accenture & Intuit). Prize pool: 1st ₹35,000, 2nd ₹25,000, 3rd ₹15,000. Winners: 1st Andromeda, 2nd Cosmic Crushers, 3rd Team X.",
                    attendees = 150,
                    eventLink = "https://www.spaceappschallenge.org/2025/local-events/ghaziabad/",
                    cardImageRes = R.drawable.event_nasa_a,
                    galleryImageRes = listOf(
                        R.drawable.event_nasa_a,
                        R.drawable.event_nasa_b,
                        R.drawable.event_nasa_c
                    )
                ),
                ClubEvent(
                    id = "e28",
                    title = "Git and GitHub Session",
                    date = LocalDate(2025, 2, 19),
                    description = "Innogeeks conducted an insightful session on Git and GitHub, introducing participants to the fundamentals of version control and collaborative development — tracking changes with essential Git commands, hosting repositories, collaborating with others, and contributing to open-source projects.",
                    cardImageRes = R.drawable.event_git_feb2025_b_a,
                    galleryImageRes = listOf(
                        R.drawable.event_git_feb2025_b_a,
                        R.drawable.event_git_feb2025_b_b
                    )
                )
            )
        )
    }
}
