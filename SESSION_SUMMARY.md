# MADT Lab #4 — Project Session Summary & Report

## 1. Overview and Project Scope
- **Course**: Mobile Application Development Technologies (MADT) — Lab #4
- **Project Title**: WinCalc (Calculator App inspired by Windows Calculator)
- **Application ID**: `com.example.calculator`
- **Architecture**: MVVM (Jetpack Compose UI + StateFlow + Repository + Room Database + Pure Kotlin BigDecimal Engine)

---

## 2. AI Tooling & Workflow Attribution
- **Application Generation**: Initial application generation was performed using **AI Studio**, based on the original application-generation prompt, confirmed project decisions (App Name: `WinCalc`, Package: `com.example.calculator`, Persistence: `Room Database`), and user approval of the implementation plan.
- **Code Review, Refinements & Documentation**: Subsequent development, code review, implementation refinements, and documentation were performed using **Gemini in Android Studio**.
- **Prompts**: Prompts used for development and documentation sessions were generated from user conversations with **ChatGPT**.

---

## 3. Four-Session Progress Tracker

| Session | Purpose | Status | Key Deliverables |
|---|---|---|---|
| **Session 1** | Application Generation & Initial Build/Run | **Completed & Verified** | Initial source code generated via AI Studio based on original prompt, approved implementation plan, Room database setup, debug signing fix, initial build and run (`BUILD SUCCESSFUL`), functional testing passed |
| **Session 2** | Independent Code Review & Priority Changes | **Completed & Verified** | Independent code review, prioritization clarification (`FINDING-04` number formatting in `CalculatorEngine.kt#formatNumber()`, `FINDING-01` header button functionality in `CalculatorScreen.kt#CalculatorScreen()`), user approval, subsequent correction passes, history/memory preservation, long-expression scrolling in `CalculatorDisplay.kt`, 28 tests passing (`BUILD SUCCESSFUL`), and successful `assembleDebug` |
| **Session 3** | Project Documentation & Compliance Mapping | **Completed & Verified** | README.md compliance table and SESSION_SUMMARY.md prepared via Gemini in Android Studio using ChatGPT-generated prompts |
| **Session 4** | GitHub Repository Setup & Publication | **Completed & Verified** | Public [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc), branch `main`, initial commit `32a76f3` ("Initial commit: WinCalc MADT Lab 4") and documentation update commit `14a62c2` ("Update project documentation and session summary"), fully committed and pushed to origin/main |

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
- **User Manually Verified**: Specific interactions listed in Section 4 above, user-confirmed project decisions, explicit confirmation that no manual code editing was performed, and explicit user approval of Session 2 priority improvements.
- **Gemini Reported & Session Reports**: Execution of the 28 automated unit and Robolectric tests (`CalculatorEngineTest`, `ExampleRobolectricTest`, `ExampleUnitTest`), architectural analysis, functional testing results (`BUILD SUCCESSFUL`), independent code review findings (`FINDING-04` and `FINDING-01`), subsequent correction passes, and build verification (`assembleDebug` successful).
- **Partially Verified / Unconfirmed Details**: Exact visual fidelity comparison against the unavailable assignment PDF.
- **Not Verified / Unresolved**: Hamburger menu navigation, window pinning functionality, full Lithuanian toast message localization, and complete independent verification of every calculator edge case.

---

## 6. Detailed Session Logs

### Session 1: Application Generation & Initial Verification
- Initial application code, UI components, and Room database structure generated via **AI Studio** based on the original application-generation prompt and confirmed project decisions (App Name: `WinCalc`, Package: `com.example.calculator`, Persistence: `Room Database`).
- User reviewed and explicitly approved the implementation plan.
- User explicitly confirmed that no manual code editing was performed during project development.
- Resolved a build-time debug signing issue by removing custom debug signing overrides in `build.gradle.kts` and utilizing standard Android Studio debug signing configuration.
- Successfully built and ran the application on an emulator (`gradle :app:testDebugUnitTest` -> `BUILD SUCCESSFUL`).
- Functional test report confirmed passing results for all arithmetic operations, unary functions, memory controls, history persistence, and localization resources.

### Session 2: Independent Code Review & Priority Changes
- Conducted independent code review and code refinements using **Gemini in Android Studio**.
- **Status**: Completed & Verified.
- **Key Deliverables & Improvements Addressed**:
  - **FINDING-04**: Number formatting implementation (`CalculatorEngine.kt#formatNumber()`).
  - **FINDING-01**: Header button functionality (`CalculatorScreen.kt#CalculatorScreen()`).
  - Subsequent correction passes addressing percentage behavior and edge cases.
  - History and memory interactions improved and preserved.
  - Long-expression horizontal scrolling implemented in `CalculatorDisplay.kt`.
  - Preservation of all previously confirmed working functionality.
  - Final verification reports confirm 28 tests passing with 0 failures (`BUILD SUCCESSFUL`) and successful `assembleDebug` execution.

### Session 3: Project Documentation
- Prepared and refined `README.md` and `SESSION_SUMMARY.md` using **Gemini in Android Studio** with prompts generated from **ChatGPT** conversations.
- **Status**: Completed and verified. No source code was modified.

### Session 4: GitHub Repository Setup, Publication, and Git Commit/Push
- **Status**: Completed and verified.
- **Repository**: [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc)
- **Branch**: `main`
- **Initial Commit**: `32a76f3` ("Initial commit: WinCalc MADT Lab 4")
- **Documentation Update Commit**: `14a62c2` ("Update project documentation and session summary")
- **Published Status**: Both initial project commit (`32a76f3`) and documentation update commit (`14a62c2`) are committed and pushed to `origin/main`.

---

## 7. Final Project Status Summary
- **Session Progress**: Sessions 1–4 have been completed and verified according to the recorded workflow. GitHub publication, initial project commit `32a76f3`, and documentation commit `14a62c2` are verified and pushed to `main`. Core implementation, independent code review improvements (`FINDING-04` and `FINDING-01`), build verification, test suites (28 tests passing, `BUILD SUCCESSFUL`), and documentation are fully verified based on session evidence and test reports.
- **GitHub Publication & Commit/Push**: Initial commit `32a76f3` and documentation commit `14a62c2` successfully published and pushed to [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc) (`main`).
- **User Manually Verified**: Specifically limited to the user-reported observations listed in Section 4, implementation plan approval, explicit confirmation that no manual code editing was performed, and explicit user approval of Session 2 priority improvements.
- **Gemini Reported & Session Reports**: 28 automated tests passing (`BUILD SUCCESSFUL`, 24 unit tests in `CalculatorEngineTest`, 3 Robolectric tests in `ExampleRobolectricTest`, 1 unit test in `ExampleUnitTest`), alongside successful `assembleDebug`.
- **Partially Verified Aspects**: Exact visual fidelity comparison against the assignment PDF.
- **Unresolved/Unverified Aspects**: Assignment PDF unavailable; hamburger menu and window pinning are static placeholders; minor Lithuanian localization gaps exist.
