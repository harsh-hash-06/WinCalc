# WinCalc — Android Calculator App (MADT Lab #4)

## Project Overview and Purpose
**WinCalc** is an Android calculator app inspired by the Windows Calculator design, developed as part of **Mobile Application Development Technologies (MADT Lab #4)**. It implements standard arithmetic operations, unary functions, calculation history, and localization using Jetpack Compose, Kotlin, and MVVM architecture.

- **GitHub Repository**: [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc)
- **Session Progress Report**: [SESSION_SUMMARY.md](SESSION_SUMMARY.md)

---

## Application Generation & Development Workflow
- **Application Generation**: Initial application generation was performed using **AI Studio**, based on confirmed project decisions (App Name: `WinCalc`, Package: `com.example.calculator`, Persistence: `Room Database`) and an approved implementation plan.
- **Development & Refinements**: Subsequent code review, implementation refinements, and documentation were performed using **Gemini in Android Studio**.
- **Prompts**: Prompts used during development and documentation sessions were generated from user conversations with **ChatGPT**.

---

## Official Assignment Compliance

The following table evaluates compliance against the explicit requirements defined in the MADT Lab #4 assignment instructions and workflow guidelines.

| Official Assignment Requirement | Relevant Implementation / Process Evidence | Status | Explanation / Evidence Notes |
|---|---|---|---|
| Code generation started using `/grill-me` or `/plan`. | AI Studio was used for initial application generation based on confirmed project decisions and user-approved implementation plan. | **Partially verified** | AI Studio application generation workflow and implementation plan approval are verified, but exact chat invocation logs for `/grill-me` or `/plan` are not present in repository files. |
| No manual code editing; Android Studio used for build, run, and code review. | IDE-managed build and deployment workflows via Gradle inside Android Studio, supported by compilation and test execution reports (`BUILD SUCCESSFUL`). | **Partially verified** | Android Studio build, run, and review workflows are verified via Gradle test reports, but absolute proof that no manual code editing occurred across all development phases cannot be independently confirmed from repository files alone. |
| Separate code review session and implementation of the two highest-priority review changes. | Independent review and code refinements were conducted using Gemini in Android Studio. | **Partially verified** | Review activity and code refinements are verified, but the exact two highest-priority review findings selected and their specific implementation changes cannot be independently confirmed from repository files alone. |
| Separate documentation session. | Documentation prepared using Gemini in Android Studio with prompts generated from ChatGPT conversations. | **Verified** | Documentation files (`README.md`, `SESSION_SUMMARY.md`) exist in the project root. |
| README creation and publication as GitHub repository description. | README created locally, published, and updated via commit `37255a1` ("Update README documentation") on `main`. | **Verified** | Published in public repository [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc). |
| Separate GitHub commit/push session. | Session completed with initial project commit `32a76f3` ("Initial commit: WinCalc MADT Lab 4") and subsequent documentation update commit `37255a1` pushed to `main`. | **Verified** | Code committed and pushed to [WinCalc GitHub Repository](https://github.com/harsh-hash-06/WinCalc). |
| Separate AI sessions for generation, review, documentation, and GitHub interaction. | Multi-stage workflow involving AI Studio (generation), Gemini in Android Studio (review/refinements/documentation), and ChatGPT-generated prompts. | **Verified** | Structured multi-session workflow is documented and evidenced across project history and summaries. |
| Session summary emphasizing user input and creation of an MD file. | `SESSION_SUMMARY.md` created with a dedicated user manual verification log and implementation report evidence. | **Verified** | Markdown summary file exists in project root. |
| Windows Calculator design shown in the assignment PDF. | Keypad layout (6×4) and UI styling inspired by Windows Calculator, supported by Session 1 implementation report. | **Partially verified** | Windows-inspired design and 6×4 keypad are fully implemented, but the assignment PDF is unavailable for exact visual fidelity comparison. |
| Required operations: addition, subtraction, multiplication, division, square root, square, percentage, and reciprocal/fraction. | Implemented in `CalculatorEngine` and verified via Session 1 functional test report (`BUILD SUCCESSFUL`). | **Verified** | Implemented in code and verified by unit tests (`gradle :app:testDebugUnitTest`). |
| Additional controls: delete a digit/character, clear input, change sign. | Implemented via Backspace (⌫), Clear Entry (CE), Clear (C), and sign toggle (+/−); tested and verified. | **Verified** | Implemented in code and verified. |
| Runtime calculation history. | Calculation history managed in ViewModel and displayed via History Panel; tested and verified. | **Verified** | Implemented and verified via test report. |
| Calculation module implemented from scratch without a third-party calculation library. | `CalculatorEngine.kt` uses custom Kotlin logic and `BigDecimal`; verified. | **Verified** | Pure Kotlin implementation without external math libraries. |
| Button text easily localizable and not hard-coded in application source. | Strings stored in `res/values/strings.xml` and `res/values-lt/strings.xml`; verified. | **Verified** | Localized string resources used across UI components. |

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
- **Status**: Session 1 review report and Gemini-reported test results indicate 100% passing (`BUILD SUCCESSFUL`, 0 failures, 0 errors).

---

## Known Limitations & Incomplete Features
- **Hamburger Menu**: Header menu icon is a static placeholder and does not open a navigation drawer.
- **Window Pinning**: Pin icon in the header is non-functional on Android (desktop feature).
- **Localization Gaps**: Minor Lithuanian localization gaps may exist in dynamic runtime messages.
