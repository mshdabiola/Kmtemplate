# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
[Unreleased]: https://github.com/mshdabiola/kmtemplate/compare/1.2.16...HEAD

### Added
- UI tests for `KmtSnackBar` to verify its display for different states (`Default`, `Error`, `Success`, `Warning`).
- Baseline profile generation support.
- `showUpdateDialog` setting to `UserPreferences` and `UserDataRepository` to control update notification visibility.
- `updateFromPreRelease` setting to `UserDataRepository` to control whether pre-release versions are considered for updates.
- Corresponding tests for the new user preference settings in relevant test files.

### Changed
- Refined version checking in `RealNetworkRepository` to correctly return `ReleaseInfo.UpToDate` when the installed version is identical to the latest online version. Updated `NetworkRepositoryTest` accordingly.
- Added a `Modifier` parameter and a `testTag("KmtSnackBar")` to the `KmtSnackBar` composable to facilitate UI testing.
- Updated the Play Store full description file (`fastlane/metadata/android/en-US/full_description.txt`) to use HTML tags for formatting instead of Markdown.
- Updated Android Gradle Plugin to 8.12.0.
- Refactored version check to use version name.
- Enhanced version validation logic in `RealNetworkRepository`.
- Updated F-Droid metadata for Kmtemplate.
- **CI**: Enabled `setVersionFromTag` task in the Release GitHub Actions workflow.
- **Documentation**:
  - Updated and reformatted development commands.
  - Removed Detekt Compose configuration and module graph from documentation.
- Updated `UserPreferencesDataSource` and its implementations (`RealUserPreferencesDataSource` for nonJsMain and wasmJsMain) to include `setShowUpdateDialog`.
- Updated `UserDataRepository` and its implementations (`RealUserDataRepository`, `FakeUserDataRepository`) to include `setUpdateFromPreRelease` and `setShowUpdateDialog`.
- `MainAppViewModelTest`: Adjusted tests to reflect new user settings (`showUpdateDialog`, `updateFromPreRelease`) and their impact on update checks.
- `ParsedVersion`: Modified `fromString` to allow parsing of version strings with trailing characters by making the fallback `simplerRegex` more lenient (e.g., "1.2.3.4" is now parsed as "1.2.3").
- `ParsedVersionTest`: Updated tests to align with the modified parsing behavior in `ParsedVersion.fromString`.

## [1.2.16] - 2025-08-14
[1.2.16]: https://github.com/mshdabiola/kmtemplate/1.2.16


### Added
- `WideNavigationRailItem` composable for displaying a wider navigation item, typically used when the navigation rail is expanded. (Conceptual addition, assuming it was newly created or significantly fleshed out based on our discussion).

### Changed
- Refactored `ReleaseUpdateDialog.kt`:
  - Moved hardcoded strings ("New Update Available", "Download", "Cancel") to `core/ui/src/commonMain/composeResources/values/strings_ui.xml`.
  - Updated `ReleaseUpdateDialog` composable to use `stringResource` for these texts.
- Suppressed `ktlint(standard:class-naming)` lint warning for `external object window` in `app/src/wasmJsMain/kotlin/com/mshdabiola/kmtemplate/LocalizationWrapper.wasmJs.kt` to allow lowercase naming for JavaScript interop.


## [1.2.15] - 2025-08-12
[1.2.15]: https://github.com/mshdabiola/kmtemplate/1.2.15

## [1.2.14] - 2025-08-11
[1.2.14]: https://github.com/mshdabiola/kmtemplate/1.2.14

### Added
- `PrependUnreleasedToChangelogTask`: New Gradle task to add an "Unreleased" section to `CHANGELOG.md` after a release, preparing for the next development cycle.
- Integrated `PrependUnreleasedToChangelogTask` into `CiTaskPlugin`.

### Changed
- `SetVersionFromTagTask`:
  - Now updates `CHANGELOG.md` to set the release version and date for the "[Unreleased]" section.
  - Correctly updates and adds link definitions for the released version and the new "[Unreleased]" section.
- `CiTaskPlugin`:
  - `setVersionFromTag` task now correctly passes the `changelogFile` parameter.
  - Added and configured `prependUnreleasedChangelog` task.

## [0.0.5] - 2025-08-10
[0.0.5]: https://github.com/mshdabiola/kltemplate/0.0.5

### Added

#### Baseline Profile Support
- New `benchmarks` module for generating baseline profiles
- `GenerateBaselineProfile` class for baseline profile collection
- `StartupBaselineProfile` class for startup-specific profile generation
- Managed virtual device configuration (`pixel6Api33`) with Pixel 6 device profile, API level 33
- Baseline profile plugin integration in app module
- AndroidJUnitRunner configuration for instrumentation tests in benchmarks
- Self-instrumenting experimental property enabled for baseline profiles

#### Build Configuration
- Baseline profile dependencies (AndroidX Benchmark Macro, Test Runner, UI Automator, Profile Installer)
- JVM toolchain version set to 21 for benchmarks module

### Changed

#### App Module
- Simplified `app/build.gradle.kts` dependencies:
  - Replaced multiple project and library dependencies with single `projects.library` dependency
  - Removed platform-specific desktop dependencies (commented out)
  - Removed Android-specific dependencies (koin.android, androidx.core.ktx, androidx.lifecycle.runtimeCompose, androidx.window.core, kermit.koin)
  - Removed `androidMain` and `jvmTest` source set dependencies entirely
  - `jvmMain` no longer includes kermit.koin dependency

#### Application Structure
- `SamApplication` class simplified - removed all initialization logic (Logger, Koin DI startup)
- `SamApp` composable now displays `NoteCard` with fixed "Title" and "Content" instead of simple "Texting" text
- Main entry points simplified:
  - JVM: `mainApp()` renamed to `main()`, removed Koin and logging setup
  - WASM/JS: Removed Koin DI and logging from `main()` function
  - Removed platform detection logic (`getPlatform()` function)

#### Build Logic
- `AndroidLibraryConventionPlugin`: Removed all dependency declarations from Kotlin multiplatform source sets
- `KoverConventionPlugin`: Changed excluded subprojects from ["core", "model", "testing"] to ["benchmarks"]
- Root `build.gradle.kts`: Removed plugin aliases for `kotlin.serialization`, `ksp`, and `room`

#### Documentation
- Benchmarks README: Changed from "Network Module Graph" to "Benchmarks Module Graph" with simplified bidirectional relationship between benchmarks and app

### Removed

#### Complete Modules Deleted

##### Core Data Module (`core/data/`)
- Entire module with build configuration, ProGuard rules
- Repository interfaces: `NetworkRepository`, `NoteRepository`, `UserDataRepository`
- Repository implementations: `RealNetworkRepository`, `RealNoteRepository`, `RealUserDataRepository`, `RealModelRepository`
- Model extensions for converting between `UserPreferences` and `UserSettings`
- Dependency injection modules: `DataModule` with platform-specific implementations
- Platform-specific implementations for Android, JVM, and WASM/JS
- Test classes: `NetworkRepositoryTest`, `NoteRepositoryTest`, `UserDataRepositoryTest`
- Test doubles: `TestNetworkDataSource`, `TestNoteDao`, `TestUserPreferenceDataSource`
- Network monitoring utilities: `ConnectivityManagerNetworkMonitor`, `NetworkMonitor` interface

##### Core Database Module (`core/database/`)
- Entire module with Room database configuration
- `SamDatabase` abstract class with Room annotations
- `NoteDao` interface with CRUD operations
- `NoteEntity` data class for database table
- Database migrations and constants
- Platform-specific database builders for Android and JVM
- Database schemas for versions 1 of `KmtDatabase`, `SamDatabase`, and `SkeletonDatabase`
- Test class: `NoteDaoTest`

##### Core Datastore Module (`core/datastore/`)
- Entire module for user preferences persistence
- `UserPreferencesDataSource` interface
- `RealUserPreferencesDataSource` implementations for different platforms
- `UserPreferences` data model
- `UserDataJsonSerializer` for JSON serialization
- Platform-specific DataStore configurations
- Test class: `RealUserPreferencesRepositoryTest`

##### Core Model Module (`core/model/`)
- Entire module with domain models
- `DarkThemeConfig` enum (FOLLOW_SYSTEM, LIGHT, DARK)
- `Platform` sealed class with Web, Desktop, and Android subclasses
- `Flavor` enum (GooglePlay, FossReliant) and `BuildType` enum (Release, Debug)
- `ReleaseInfo` sealed class with Error and Success subclasses
- `Result` sealed interface with Success, Error, and Loading states
- `UserSettings` data class
- Extension function `Flow<T>.asResult()`

##### Core Network Module (`core/network/`)
- Entire module with Ktor HTTP client setup
- `NetworkDataSource` interface
- `RealNetworkDataSource` implementation
- HTTP client configuration with platform-specific implementations
- Network models: `Asset`, `GitHubReleaseInfo`
- Network module dependency injection
- ProGuard consumer rules for network serialization
- Test class: `NetworkSourceTest`

##### Core Testing Module (`core/testing/`)
- Entire module with testing utilities
- Fake data and repositories: `FakeNetworkRepository`, `FakeNoteRepository`, `FakeUserDataRepository`
- Test data module for dependency injection
- Test dispatcher module with `UnconfinedTestDispatcher`
- `MainDispatcherRule` for JVM coroutine testing
- Test logger configuration using Kermit
- Sample test data (list of 10 Note objects)

##### Core UI Module (`core/ui/`)
- Directory structure removed (only .gitignore was present)

#### Application Components
- `MainAppViewModel` class with UI state management
- `MainActivityUiState` sealed interface with Loading and Success states
- `ApplicationModule` providing ViewModel dependencies

#### Dependency Injection
- All Koin-related code and modules removed throughout the project
- Kermit logging initialization removed from all entry points

#### Dependencies Removed
- Koin (core, android, test)
- Kermit logging library
- Room database
- KSP (Kotlin Symbol Processing)
- Kotlin serialization
- Ktor client libraries
- AndroidX DataStore
- Okio
- Various AndroidX libraries (core.ktx, lifecycle.runtimeCompose, window.core)
- SLF4J
- Kotlinx coroutines test libraries

#### Configuration Files
- Multiple `.gitignore` files in core modules
- ProGuard rules for various modules
- AndroidManifest.xml files in core modules (were mostly empty)
