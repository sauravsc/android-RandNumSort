# Changelog

## 2.0.0 - 2026-11-27

Tooling refresh of the 2017 app. The app works as before.

- Gradle 3.3 -> 9.9.3 and Android Gradle Plugin 2.3.3 -> 9.5.3, with a version catalog
  (`gradle/libs.versions.toml`), `plugins {}` blocks, `namespace` in `app/build.gradle` and
  repositories in `settings.gradle` (JCenter is gone).
- Android Support Library -> AndroidX (AppCompat, ConstraintLayout, Fragment,
  LocalBroadcastManager); framework fragments replaced with AndroidX fragments.
- `compileSdk` 37 (minor API level 2), `targetSdk` 37; `minSdk` 21 -> 23, the minimum for current
  AndroidX libraries. Java 17 bytecode.
- Changes needed for the new target SDK: `android:exported` on the launcher activity, and system bar
  insets applied to the content so the buttons are not hidden under the navigation bar now that the
  app is drawn edge-to-edge.
- The generator screen now unregisters its broadcast receivers when its view is destroyed, so
  returning to it and sorting again no longer stacks duplicate receivers. The sorted-results listener
  is attached in `onAttach`, so it survives activity re-creation.
- Lint is clean (no errors or warnings): hard-coded labels moved to `strings.xml`, redundant RTL
  margins removed, `FragmentContainerView` as the fragment container.
- Unit tests for `MergeSort` and `QuickSort`; `QuickSort` returns early for lists with fewer than
  two elements instead of throwing on an empty list.
- GitHub Actions CI builds the debug APK, runs lint and unit tests, and compiles the instrumented tests.

## 1.0 - 2017-10-02

Initial version.
