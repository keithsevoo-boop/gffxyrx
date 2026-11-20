@echo off
setlocal
cd /d "%~dp0"
echo Prism Settings Android 11 builder
where java >nul 2>nul || (echo ERROR: Install JDK 17 first. & pause & exit /b 1)
if exist gradlew.bat (
  call gradlew.bat --no-daemon assembleDebug
) else (
  where gradle >nul 2>nul || (echo ERROR: Gradle not installed. Open this project in Android Studio, or install Gradle 8.7. & pause & exit /b 1)
  call gradle --no-daemon assembleDebug
)
if errorlevel 1 (echo BUILD FAILED. & pause & exit /b 1)
echo APK: app\build\outputs\apk\debug\app-debug.apk
pause
