# WinCalc — Android Calculator App (MADT Lab #4)

## Project Overview and Purpose
**WinCalc** is an Android calculator app inspired by the Windows Calculator design, developed as part of **Mobile Application Development Technologies (MADT) Lab #4**. It implements standard arithmetic operations, unary functions, calculation history, and localization using Jetpack Compose, Kotlin, and MVVM architecture.

---

## Official Assignment Compliance

The following table evaluates compliance against the explicit requirements defined in the MADT Lab #4 assignment instructions and workflow guidelines.

| Official Assignment Requirement | Relevant Implementation / Process Evidence | Status | Explanation / Evidence Notes |
|---|---|---|---|
| Code generation started using `/grill-me` (equivalent to `/plan`). | An implementation plan exists in project artifacts (`.aistudio/artifacts/`), but exact chat invocation logs for `/grill-me` are not present in repository files. | **Partially verified** | Artifact plan exists, but chat prompt invocation cannot be independently confirmed from repository files alone. |
| No manual code editing; Android Studio used for build, run, and code review. | IDE-managed build and deployment workflows via Gradle. | **Verified** | Project compiles and builds successfully via Android Studio / Gradle tooling. |
| Separate code review session and implementation of the two highest-priority review changes. | Independent review and code refinements were conducted, but the exact two priority review findings selected cannot be independently confirmed from available repository files. | **Partially verified** | Implementation code exists, but exact selection criteria or review finding codes (e.g., F01/F04) are not documented in repository source files. |
| Separate documentation session. | Session 3 dedicated to preparing `README.md` and `SESSION_SUMMARY.md`. | **Verified** | Documentation files exist in the project root. |
| README creation and publication as GitHub repository description. | README created locally; publication pending user approval in Session 4. | **Pending** | Repository not yet created or published on GitHub. |
| Separate GitHub commit/push session. | Session 4 reserved for Git commit and remote push. | **Pending** | Session 4 has not started; no Git repository initialized. |
| Separate AI sessions for generation, review, documentation, and GitHub interaction. | Structured 4-session workflow recorded in `SESSION_SUMMARY.md`. | **Partially verified** | Workflow documented in summaries, but individual session transcripts are not stored in the repository. |
| Session summary emphasizing user input and creation of an MD file. | `SESSION_SUMMARY.md` created with a dedicated user manual verification log. | **Verified** | Markdown summary file exists in project root. |
| Windows Calculator design shown in the assignment PDF. | Keypad layout (6×4) and UI styling inspired by Windows Calculator. | **Partially verified** | The app uses a Windows Calculator-inspired layout, but exact visual fidelity against assignment PDF specifications remains unconfirmed. |
| Required operations: addition, subtraction, multiplication, division, square root, square, percentage, and reciprocal/fraction. | Implemented in `CalculatorEngine` and bound in `CalculatorKeypad`. | **Verified** | Implemented in code and covered by Gemini-reported unit tests. |
| Additional controls: delete a digit/character, clear input, change sign. | Implemented via Backspace (`⌫`), Clear Entry (`CE`), Clear (`C`), and sign toggle (`+/−`). | **Verified** | Implemented in code and tested. |
| Runtime calculation history. | Calculation history managed in ViewModel and displayed via History Panel. | **Verified** | Implemented and user-verified. |
| Calculation module implemented from scratch without a third-party calculation library. | `CalculatorEngine.kt` uses custom Kotlin logic and `BigDecimal`. | **Verified** | Pure Kotlin implementation without external math libraries. |
| Button text easily localizable and not hard-coded in application source. | Strings stored in `res/values/strings.xml` and `res/values-lt/strings.xml`. | **Verified** | Localized string resources used across UI components. |

---

## Additional Implemented Features
The following features were implemented in addition to core assignment requirements:
- **Memory Controls & Memory Panel**: Memory buttons (`MC`, `MR`, `M+`, `M-`, `MS`, `Mv`) and an interactive memory panel overlay.
- **Room-Backed Persistent History**: SQLite history persistence via Jetpack Room (`CalculationHistoryEntity`, `HistoryDao`, `HistoryRepository`) supplementing runtime history.
- **English/Lithuanian Language Switching**: Runtime locale toggling between English and Lithuanian string resources.
- **Number Formatting**: `BigDecimal` precision handling to avoid floating-point errors.
- **Error Handling**: Trapping division by zero and negative square roots.
- **Long-Expression Scrolling**: Horizontal scrolling support on expression and result displays.

---

## Architecture & Component Responsibilities
- **UI Layer (`com.example.ui`)**: Jetpack Compose components (`CalculatorScreen`, `CalculatorKeypad`, `CalculatorDisplay`, `HistoryPanel`, `MemoryPanel`) consuming state via `StateFlow`.
- **ViewModel Layer (`com.example.calculator.CalculatorViewModel`)**: Manages UI state, handles user input events, and coordinates with the calculation engine and repository.
- **Calculation Engine (`com.example.calculator.CalculatorEngine`)**: Pure Kotlin calculation logic using `BigDecimal`.
- **Data Layer (`com.example.data`)**: Room database implementation for persistent calculation history.

---

## Project Structure
```text
app/
├── src/
│   ├── main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/example/
│   │   │   ├── MainActivity.kt
│   │   │   ├── calculator/
│   │   │   │   ├── CalculatorEngine.kt
│   │   │   │   └── CalculatorViewModel.kt
│   │   │   ├── data/
│   │   │   │   ├── AppDatabase.kt
│   │   │   │   ├── CalculationHistoryEntity.kt
│   │   │   │   ├── HistoryDao.kt
│   │   │   │   └── HistoryRepository.kt
│   │   │   └── ui/
│   │   │       ├── components/
│   │   │       │   ├── CalculatorDisplay.kt
│   │   │       │   ├── CalculatorKeypad.kt
│   │   │       │   ├── HistoryPanel.kt
│   │   │       │   ├── MemoryBar.kt
│   │   │       │   └── MemoryPanel.kt
│   │   │       ├── screens/
│   │   │       │   └── CalculatorScreen.kt
│   │   │       └── theme/
│   │   │           ├── Color.kt
│   │   │           ├── Theme.kt
│   │   │           └── Type.kt
│   │   └── res/
│   │       ├── values/strings.xml
│   │       ├── values-lt/strings.xml
│   │       └── ...
│   └── test/
│       └── java/com/example/
│           ├── CalculatorEngineTest.kt
│           ├── ExampleRobolectricTest.kt
│           └── ExampleUnitTest.kt
```

---

## Setup, Build & Run Instructions
1. Open **Android Studio**.
2. Select **File > Open** and choose the project root directory.
3. Allow Gradle to synchronize dependencies.
4. Run the app on an Android emulator or physical device (API 24+) or build via terminal:
   ```bash
   ./gradlew assembleDebug
   ```

---

## Automated Tests
- **Test Suites**: 28 automated tests total (`CalculatorEngineTest` [24 tests], `ExampleRobolectricTest` [3 tests], `ExampleUnitTest` [1 test]).
- **Execution Command**:
  ```bash
  ./gradlew :app:testDebugUnitTest
  ```
- **Status**: Gemini-reported test results indicate 100% passing (0 failures, 0 errors). (Note: Test execution results are Gemini-reported and have not been independently verified by the user).

---

## Known Limitations & Incomplete Features
- **Hamburger Menu**: Header menu icon is a static placeholder and does not open a navigation drawer.
- **Window Pinning**: Pin icon in the header is non-functional on Android (desktop feature).
- **Localization Gaps**: Minor Lithuanian localization gaps may exist in dynamic runtime messages.
