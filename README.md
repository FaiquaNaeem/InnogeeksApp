# Innogeeks Club App

Native Android app for the Innogeeks tech club. It lets anyone browse club content as a guest, self-register, track their membership journey, and — once promoted — access member-only tools like attendance and resources.

## Demo

<video src="https://github.com/mahakaal2005/InnogeeksApp/raw/main/videos/Guest_Demo.mp4" controls width="100%">
  Your browser does not support the video tag.
</video>

---

## Tech Stack

| Concern | Library | Version |
|---|---|---|
| Language / UI | Kotlin + Jetpack Compose + Material 3 | Kotlin 2.3.20 / BOM 2026.06.01 |
| Navigation | Navigation Compose (type-safe `@Serializable` routes) | 2.8.4 |
| DI | Koin | 4.0.0 |
| Networking | Ktor Client (OkHttp engine) + kotlinx-serialization | 3.0.1 |
| Local database | Room | 2.7.0 |
| Key/value + tokens | Jetpack DataStore Preferences | 1.1.1 |
| Blur / glassmorphism | Haze | 2.0.0-alpha03 |
| Async | Kotlin Coroutines + Flow | 1.9.0 |
| Min SDK | Android 7.0 | API 24 |
| Target / Compile SDK | — | 36 / 37 |

---

## Architecture

Single Gradle module (`:app`) organized as **Feature-Driven Clean Architecture** with **MVI (Unidirectional Data Flow)** inside MVVM.

```
Presentation  ──depends on──▶  Domain  ◀──depends on──  Data
     │                           │                         │
  Compose UI                 Use Cases                  Ktor / Room
  ViewModel                  Repository                 DataStore
  State / Action             interfaces                 Mappers
  Event channel              Domain models              DTOs / Entities
```

**Key rules:**
- Domain has zero Android imports and never imports Data or Presentation.
- Features never import each other — shared contracts live in `core/domain`.
- `Result<T, DataError>` (typed errors) is the transport between Data and Domain. The ViewModel unpacks it into flat `State` fields before the UI ever sees it.
- Loading is a plain `isLoading: Boolean` in State, never part of `Result`.

---

## Package Structure

```
com.example.innogeeks/
│
├── InnogeeksApp.kt              ← Application; assembles all Koin modules
├── MainActivity.kt              ← Single activity, hosts the NavHost
│
├── core/
│   ├── common/                  ← Constants (BASE_URL, etc.)
│   ├── domain/
│   │   ├── error/               ← DataError (Network / Local), ApiFailure
│   │   ├── model/               ← UserRole enum
│   │   ├── session/             ← Session sealed class, SessionRepository interface
│   │   └── util/                ← Result<T,E>, EmptyResult
│   ├── data/
│   │   ├── networking/          ← HttpClientFactory, HttpClientExt (safeCall), ApiEnvelope
│   │   └── session/             ← DataStoreSessionRepository
│   ├── navigation/              ← AppRoutes (shared @Serializable route objects)
│   ├── presentation/
│   │   ├── components/          ← GlassComponents, StatTile, SectionLabel, ExpandableRow
│   │   ├── mapper/              ← DataErrorToUiText
│   │   ├── ObserveAsEvents.kt
│   │   └── UiText.kt
│   └── di/                      ← coreDataModule
│
├── feature_onboarding/          ← Login, email gate, registration
├── feature_home/                ← Guest Bento-grid home + MainScaffold (bottom nav shell)
├── feature_domains/             ← Domain browser (Android, Web, ML, IoT, AR/VR)
├── feature_events/              ← Club events timeline (past + upcoming tabs)
└── feature_profile/             ← Profile tab (session-derived, no data module)
```

Each feature follows the same internal shape:

```
feature_x/
├── domain/         ← models, repository interfaces, use cases
├── data/           ← DTOs, DAOs, repository impls, Koin data module
└── presentation/   ← State / Action / Event / ViewModel / Screen, Koin presentation module
```

---

## Features

### Implemented

| Feature | What it does |
|---|---|
| **Onboarding** | Email gate → login (LoginUseCase) and self-serve registration (SubmitRegistrationUseCase + AuthValidator). Ktor-backed (`KtorAuthDataSource`) with a `FakeAuthDataSource` for local dev. |
| **Home** | Guest Bento-grid: hero section, club stats, achievements row, domain wheel, keyword ticker, class culture card. Session-aware — logged-in users will see the tracker dashboard (Phase 2). |
| **Domains** | 2-column gradient card grid for all five club domains with expandable detail and domain signature icons. |
| **Events** | Full vertical timeline with Past / Upcoming tab filter and event card components. |
| **Profile** | Role-aware tab backed purely by the active session (no network call). Login CTA for guests. |

### Role-Based Navigation

| Role | Tabs | Notes |
|---|---|---|
| Guest | Home · Domains · Events · Profile | No login wall. Full public browse. |
| Registered | Home · Domains · Resources · Events · Profile | 5 tabs. Home becomes a membership tracker. |
| Member | Home · Attendance · Resources · Profile | 4 tabs; Events deferred. |
| Coordinator | Home · Attendance · Resources · Profile | Same shape as Member, elevated permissions per tab. |

Navigation is session-driven: `HomeNavGraph` collects `Session` from `SessionRepository` and passes it into `MainScaffold`, which renders the correct tab set with no re-login required.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 11+
- Android device or emulator running API 24+

### Clone & Run

```bash
git clone https://github.com/<your-org>/InnogeeksApp.git
cd InnogeeksApp
```

Open the project in Android Studio, let Gradle sync, then run the `app` configuration on your device or emulator.

### API Base URL

The base URL is injected at build time via `BuildConfig.BASE_URL`. It is currently hardcoded in `app/build.gradle.kts`:

```kotlin
buildConfigField("String", "BASE_URL", "\"https://api.innogeeks.example\"")
```

Replace the placeholder value with the actual backend URL before testing network features.

---

## Development Notes

- **In-memory repositories** (`InMemoryAuthRepository`, `InMemoryHomeRepository`, etc.) stand in for real network/DB calls during UI development. Swap them for Ktor/Room implementations in the data Koin modules when the backend is ready.
- **Haze is alpha** (`2.0.0-alpha03`). The glassmorphism bottom nav bar may have API changes on upgrade.
- **Single module for now.** The package boundaries already mirror a multi-module layout (`core/domain`, `feature_x/data`, etc.), so splitting into real Gradle modules later is mostly moving folders.
- All screens must have `@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)`. The app is dark-mode only; never use `showBackground = true` in previews.

---

## Roadmap

| Phase | Scope |
|---|---|
| Phase 1 ✅ | Guest browse + registration + login |
| Phase 2 | Registered-user tracking dashboard + payment step |
| Phase 3 | Member experience (Attendance, Resources) |
| Phase 4 | Coordinator experience (elevated permissions per tab) |
| Phase 5 | Admin surface (form TBD — in-app vs. web dashboard) |