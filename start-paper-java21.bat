@echo off
setlocal

set "JAVA21=C:\Program Files\Java\jdk-21.0.12\bin\java.exe"
set "SERVER_JAR=paper-1.26.2.jar"
set "MIN_MEM=2G"
set "MAX_MEM=4G"

if not exist "%JAVA21%" (
  echo [ERROR] Java 21 not found: %JAVA21%
  exit /b 1
)

if not exist "%SERVER_JAR%" (
  echo [ERROR] Server jar not found: %SERVER_JAR%
  echo Place this script in your server folder, then set SERVER_JAR to your actual jar file name.
  exit /b 1
)

:loop
"%JAVA21%" -Xms%MIN_MEM% -Xmx%MAX_MEM% -jar "%SERVER_JAR%" nogui
echo.
echo Server stopped. Restarting in 5 seconds... Press Ctrl+C to abort.
timeout /t 5 /nobreak >nul
goto loop
