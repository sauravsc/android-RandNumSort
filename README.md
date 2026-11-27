# RandNumSort

[![CI](https://github.com/sauravsc/android-RandNumSort/actions/workflows/ci.yml/badge.svg)](https://github.com/sauravsc/android-RandNumSort/actions/workflows/ci.yml)

An Android app that generates 40 random two-digit numbers and sorts them with merge sort and quick sort
in two background services, showing both sorted lists side by side.

## Requirements

- JDK 17 or newer to run Gradle (CI uses JDK 21)
- Android SDK with platform `android-37.2` (Android Gradle Plugin downloads it if missing and the SDK
  licences are accepted)
- Runs on Android 6.0 (API 23) and newer; targets API 37

## Build

```sh
export ANDROID_HOME=/path/to/Android/sdk   # or create local.properties with sdk.dir=...
./gradlew assembleDebug lint test
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`. Install it on a device or
emulator with `adb install app/build/outputs/apk/debug/app-debug.apk`.

Instrumented tests (`app/src/androidTest`) need a device or emulator: `./gradlew connectedDebugAndroidTest`.

Debug APKs are attached to each [GitHub release](https://github.com/sauravsc/android-RandNumSort/releases).
