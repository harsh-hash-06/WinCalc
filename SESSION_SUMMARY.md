# MADT Lab #4 — Project Session Summary & Report

## 1. Overview and Project Scope
- **Course**: Mobile Application Development Technologies (MADT) — Lab #4
- **Project Title**: WinCalc (Calculator App inspired by Windows Calculator)
- **Application ID**: `com.example.calculator`
- **Architecture**: MVVM (Jetpack Compose UI + StateFlow + Repository + Room Database + Pure Kotlin BigDecimal Engine)

---

## 2. AI Tooling & Workflow Attribution
- **Application Generation**: Initial application generation was performed using **AI Studio**, based on confirmed project decisions (App Name: `WinCalc`, Package: `com.example.calculator`, Persistence: `Room Database`) and user approval of the implementation plan.
- **Code Review, Refinements & Documentation**: Subsequent development, code review, implementation refinements, and documentation were performed using **Gemini in Android Studio**.
- **Prompts**: Prompts used for development and documentation sessions were generated from user conversations with **ChatGPT**.

---

## 3. Four-Session Progress Tracker

| Session | Purpose | Status | Key Deliverables |
|---|---|---|---|
| **Session 1** | Application Generation & Initial Build/Run | **Completed & Verified** | Initial source code generated via AI Studio, Room database setup, debug signing fix, initial build and run (`BUILD SUCCESSFUL`), functional testing passed |
| **Session 2** | Independent Code Review & Priority Changes | **Completed (Partially Verified Evidence)** | Code review and architectural refinements conducted via Gemini in Android Studio; completion of session is documented, but exact priority-selection evidence is unavailable |
| **Session 3** | Project Documentation & Compliance Mapping | **Completed & Verified** | README.md compliance table and SESSION_SUMMARY.md prepared via Gemini in Android Studio using ChatGPT-generated prompts |
| **Session 4** | GitHub Repository Setup & Publication | **Completed & Verified** | Public [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc), branch `main`, initial commit `32a76f3` ("Initial commit: WinCalc MADT Lab 4") and subsequent README documentation update commit `37255a1` ("Update README documentation"), fully committed and pushed to origin/main |

---

## 4. User Input and Manual Verification
The following observations represent user-reported manual checks performed during testing:
- Entering `10,000` × `10` produced `100,000`.
- Backspace after `123` + `1` = `124` changed the displayed result to `12`.
- History recall worked.
- History panel dismissed when tapping outside.
- Memory panel dismissed when tapping outside.
- `0` ÷ `0` displayed an error.
- Repeated square and square-root operations worked.
- Lithuanian History and Memory localization worked.
- Long-expression scrolling worked.

---

## 5. Evidence Types & Verification Attribution
- **User Manually Verified**: Specific interactions listed in Section 4 above, plus review and approval of the AI Studio implementation plan.
- **Gemini Reported & Session 1 Report**: Execution of the 28 automated unit and Robolectric tests (`CalculatorEngineTest`, `ExampleRobolectricTest`, `ExampleUnitTest`), architectural analysis, functional testing results (`BUILD SUCCESSFUL`), independent code review findings, and code generation outputs.
- **Partially Verified / Unconfirmed Details**: Exact chat command-line invocation logs for `/grill-me` or `/plan`; absolute proof that no manual code editing occurred across all development phases; completion of Session 2 code review is documented, but exact two highest-priority review finding codes and selection criteria are unavailable; exact visual fidelity comparison against the unavailable assignment PDF.
- **Not Verified / Unresolved**: Hamburger menu navigation, window pinning functionality, full Lithuanian toast message localization, and complete independent verification of every calculator edge case.

---

## 6. Detailed Session Logs

### Session 1: Application Generation & Initial Verification
- Initial application code, UI components, and Room database structure generated via **AI Studio** following user confirmation of project decisions (App Name: `WinCalc`, Package: `com.example.calculator`, Persistence: `Room Database`).
- User reviewed and approved the implementation plan.
- Resolved a build-time debug signing issue by removing custom debug signing overrides in `build.gradle.kts` and utilizing standard Android Studio debug signing configuration.
- Successfully built and ran the application on an emulator (`gradle :app:testDebugUnitTest` -> `BUILD SUCCESSFUL`).
- Functional test report confirmed passing results for all arithmetic operations, unary functions, memory controls, history persistence, and localization resources.

### Session 2: Independent Code Review & Priority Changes
- Conducted independent code review and code refinements using **Gemini in Android Studio**.
- **Status**: Completed (Partially Verified Evidence) — completion of the session is documented and architectural refinements were implemented, but the exact two highest-priority review findings and their selection criteria cannot be independently confirmed from available repository files.
- Recorded user manual verification feedback.

### Session 3: Project Documentation
- Prepared and refined `README.md` and `SESSION_SUMMARY.md` using **Gemini in Android Studio** with prompts generated from **ChatGPT** conversations.
- **Status**: Completed and verified. No source code was modified.

### Session 4: GitHub Repository Setup, Publication, and Git Commit/Push
- **Status**: Completed and verified.
- **Repository**: [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc)
- **Branch**: `main`
- **Initial Commit**: `32a76f3` ("Initial commit: WinCalc MADT Lab 4")
- **Documentation Update Commit**: `37255a1` ("Update README documentation")
- **Published Status**: Both initial project commit (`32a76f3`) and README update commit (`37255a1`) are committed and pushed to `origin/main`.

---

## 7. Final Project Status Summary
- **Session Progress**: Sessions 1–4 have been completed according to the recorded workflow. GitHub publication, initial project commit `32a76f3`, and README documentation commit `37255a1` are verified and pushed to `main`. Core implementation, build verification, test suites (28 tests passing), and documentation are fully verified based on the Session 1 review report and build results.
- **GitHub Publication & Commit/Push**: Initial commit `32a76f3` and README update commit `37255a1` successfully published and pushed to [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc) (`main`).
- **User Manually Verified**: Specifically limited to the user-reported observations listed in Section 4 and implementation plan approval.
- **Gemini Reported & Session 1 Report**: 28 automated tests passing (`BUILD SUCCESSFUL`, 24 unit tests in `CalculatorEngineTest`, 3 Robolectric tests in `ExampleRobolectricTest`, 1 unit test in `ExampleUnitTest`).
- **Partially Verified Aspects**: Exact `/grill-me` or `/plan` command invocation logs; absolute proof that no manual code editing occurred; Session 2 completion is documented, but exact two highest-priority review finding codes and selection criteria are unavailable; exact visual fidelity comparison against the assignment PDF.
- **Unresolved/Unverified Aspects**: Assignment PDF unavailable; hamburger menu and window pinning are static placeholders; minor Lithuanian localization gaps exist.
