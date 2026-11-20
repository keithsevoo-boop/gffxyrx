# Prism Settings — Android 11 prototype

A standalone Settings companion app for PrismOS Samsung Edition on Infinix Hot 10i PR652B. **This is not a custom ROM, not a system Settings replacement, and not a prebuilt APK.** It is an Android Studio project.

## Implemented
- Samsung One UI-inspired cards and large headings
- Blue/purple dark and light appearance with persisted preference
- Working links to Android's Wi-Fi, Bluetooth, mobile network, display, sound, app, battery, security and accessibility settings
- Font Studio preview page with the selected Inter 17px Medium specification (Inter font file **not** bundled; system sans-serif used for actual rendering)
- Planned Prism features visibly labeled as not implemented
- No privileged permissions, no root, no bootloader unlock

## Build on Windows 11 (recommended)
1. Install Android Studio and its Android SDK 35 platform, and JDK 17 (Android Studio includes a compatible JDK).
2. Open this directory as a project in Android Studio. Allow Gradle sync to finish.
3. Select **Build > Build APK(s)**.
4. Find the APK at `app/build/outputs/apk/debug/app-debug.apk`.
5. Copy it to your Android 11 phone and install it, approving installation from your file manager if prompted.

Alternative: Install Gradle 8.7 and JDK 17, then run `build-apk.bat`. The BAT expects a globally installed Gradle because a Gradle wrapper JAR is not included.

Alternative: Push the project to a GitHub repository and run **Actions > Build Prism Settings APK > Run workflow**. Download the generated APK artifact when the build succeeds.

## Limitations
- No ROM integration, no changes to protected system settings, no system-level Glass rendering, and no One UI 9 code/assets.
- Android Settings links depend on the OEM firmware and may vary.
- Android 11 API level 30 or higher required.
- Build not executed in this environment; run Android Studio/CI to compile and validate.
- Keep your stock firmware and original Settings intact.

## GitHub Actions fix (November 2026)
Removed `android-actions/setup-android@v3` from the workflow because it may attempt to install the obsolete SDK package `tools`. The workflow now uses the SDK on the GitHub-hosted Ubuntu runner and installs the needed platform/build tools directly. This change has not been CI-tested here.

## GitHub Actions SDK manager fix
The workflow now locates sdkmanager by its full SDK path and downloads official command-line tools if missing. It does not request obsolete `tools`. CI build remains unverified.
