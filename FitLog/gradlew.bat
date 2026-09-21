@echo off
setlocal
set "GRADLE_VERSION=8.9"
set "DIST_URL=https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip"
if "%GRADLE_USER_HOME%"=="" set "GRADLE_USER_HOME=%USERPROFILE%\.gradle"
set "CACHE_DIR=%GRADLE_USER_HOME%\wrapper\dists\gradle-%GRADLE_VERSION%-bin"
set "INSTALL_DIR=%CACHE_DIR%\gradle-%GRADLE_VERSION%"
set "ZIP_FILE=%CACHE_DIR%\gradle-%GRADLE_VERSION%-bin.zip"

if exist "%INSTALL_DIR%\bin\gradle.bat" goto run
if not exist "%CACHE_DIR%" mkdir "%CACHE_DIR%"
if not exist "%ZIP_FILE%" (
  echo Downloading Gradle %GRADLE_VERSION%...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "Invoke-WebRequest -UseBasicParsing -Uri '%DIST_URL%' -OutFile '%ZIP_FILE%'"
)
if exist "%INSTALL_DIR%" rmdir /s /q "%INSTALL_DIR%"
powershell -NoProfile -ExecutionPolicy Bypass -Command "Expand-Archive -Force '%ZIP_FILE%' '%CACHE_DIR%'"

:run
call "%INSTALL_DIR%\bin\gradle.bat" %*
endlocal
