# Product

<!-- impeccable:product-schema 1 -->

## Platform

android

## Users

Primary: college students at a technical club (Innogeeks) who have submitted the Join form and are
now in the pre-Member recruitment pipeline — logged in, waiting on payment/test/interview/decision.
They check this screen intermittently over days/weeks (not a single sustained session) to see
whether anything moved. Secondary: guests (not-yet-registered students) browsing Domains/Events
before deciding to join; existing Members/Coordinators using Resources/Attendance/Profile day-to-day
club operations.

## Product Purpose

Digitizes and centralizes a student tech club's operations: public browsing of what the club does
(Domains, Events) with zero friction, self-serve registration, a transparent recruitment pipeline
(Register → Pay ₹50 fee → Test → Interview → Member), and post-Member tools (Attendance, Resources).
Success for the recruitment tracker specifically: a waiting student can look at the screen for five
seconds and know exactly where they stand and what (if anything) they need to do next, without
anxiety or ambiguity.

## Positioning

Not a generic "application status" tracker — the pipeline, stages, and decision states are specific
to Innogeeks' actual recruitment process (₹50 fee, aptitude test, interview, admin promotion), and
the app is the club's own tool, not a third-party portal. The visual language (restrained dark
glassmorphism, hand-drawn display type) is already established and is itself part of what makes the
app feel like Innogeeks rather than a template.

## Operating Context

- Native Android, Jetpack Compose + Material 3, strictly dark mode (no light theme).
- Bottom tab nav is a floating glass pill (Haze-based blur), 5 tabs for a Registered (pre-Member)
  user: Tracker · Domains · Resources · Events · Profile.
- Backend is real but not yet live in this repo's dev loop — a Fake data source stands in
  (`FakeRecruitmentRemoteDataSource`), returning `RecruitmentStatus { paid, decision, decisionNote,
  testSlot }`. `Decision` is one of `PENDING | SELECTED | WAITLISTED | REJECTED`.
- MVI presentation pattern (State/Action/Event/ViewModel), previews required for every state.

## Capabilities and Constraints

- Real data fields available to the tracker today: `paid: Boolean`, `decision: Decision`,
  `decisionNote: String?`, `testSlot: { booked, startTime, endTime }`.
- Interview stage has no backend field yet (`TODO(phase2)` in code) — currently always renders as
  "Pending." This mockup may design the interview stage's presentation, but must not invent a fake
  data source field beyond what's noted as illustrative.
- Payment is architected as pluggable (manual today, gateway later) — the UI must not hard-code
  assumptions that only one payment method will ever exist.
- Resources tab exists (`feature_resources/`) — the tracker can meaningfully deep-link/CTA into it,
  as the built non-selection state already does.
- This design pass produces an **HTML mockup only**, in `specs/claude_ui/`, at a 390×844 phone
  viewport — a visual blueprint the Compose rebuild will follow, not a shipped artifact itself.
  Illustrative content beyond the 4 real fields (e.g. stage explainers) is permitted but must be
  visually/structurally labeled as synthetic in the deliverable notes, not presented as real copy.

## Brand Commitments

- Display font: Cabin Sketch (hand-drawn, characterful) for headlines/titles. Body font: Cabin.
- Dark M3 palette generated from a single seed color — primary `#8CD0EE` (light blue), secondary
  `#6CD4F3`, tertiary `#B5ECFF`, surfaces stepping `#101416` → `#323537`. Never invent a new palette;
  the tracker redesign works within these existing token values.
- "Restrained glass" identity — Haze glassmorphism blur is used for structural chrome (nav bar,
  card backgrounds), not as decoration everywhere. Apple/Linear-grade restraint, not neon or
  maximalist glow.
- No light-mode variant ever. No `showBackground = true` in previews — always
  `UI_MODE_NIGHT_YES`.

## Evidence on Hand

- Current implementation: `app/src/main/java/com/example/innogeeks/feature_recruitment/presentation/tracker/TrackerScreen.kt`
  — a vertical list of stage rows (Registered/Fee Paid/Aptitude Test/Interview/Decision), each a
  checkmark-or-empty-circle icon + title + subtitle. This is the screen being redesigned; the user's
  own words: "it just is showing me a box of registered and fees paid... like, what the hell is
  that." The stage data and states are real evidence; the presentation is what's being replaced.
- Theme tokens: `app/src/main/java/com/example/innogeeks/ui/theme/Color.kt`, `Type.kt`.
- Sibling screens for visual-language reference: `feature_domains/presentation/domains/DomainsScreen.kt`
  (ExpandableRow accordion, signature animated icons), `feature_resources/presentation/resources/ResourcesScreen.kt`.
- No existing marketing/landing reference beyond `specs/inspiration_images/innogeeks-app-flow_2.html`
  (an early HTML flow mockup, same color tokens).

## Product Principles

1. Calm confidence over gamification — this is a real pass/fail/waitlist decision affecting a
   student's club membership; the tone stays reassuring and informational, not celebratory-hype for
   every micro-step.
2. The screen answers "where do I stand and what do I do next" in the first viewport — no scrolling
   required to know the headline status.
3. Never let the visual system imply a stage is more certain than the data says — a
   pending/not-yet-scheduled state must read as genuinely open, not as a soft failure.
4. Inherit the existing glass/dark M3 identity exactly; this is a structural redesign of one screen
   inside an established world, not a new visual identity.

## Accessibility & Inclusion

No project-specific requirement beyond standard Material 3 contrast/touch-target defaults (already
a codebase convention — `contentDescription`, dark-mode-only contrast tuned via the M3 color
generator).
