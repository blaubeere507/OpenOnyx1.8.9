@echo off
rem Starts Onyx from the deobfuscated build, the same way the game folder's run.bat starts the original:
rem   libs\onyx-deobf.jar  replaces  onyx.jar          (built by: gradlew.bat build)
rem   libs\mc-named.jar    replaces  rt\libs\mc.jar    (made by:  tools\deobf.ps1 -NoSource)
rem Everything else (bundled JRE, natives, assets, options, onyx\ configs and accounts) is the game folder's own,
rem so this folder must sit inside the game folder (next to onyx.jar, jre\ and rt\).
rem
rem   run.bat           start the game (no console window)
rem   run.bat console   start with a console that shows the client log

rem ---- edit these two if you like ----
set "MC_USERNAME=Player"
set "ONYX_USER=dev"
rem -----------------------------------

setlocal EnableDelayedExpansion
set "HERE=%~dp0"
cd /d "%HERE%.."

if not exist "rt\libs" (
    echo This folder has to be inside the Onyx game folder ^(next to jre\ and rt\^).
    pause
    exit /b 1
)
if not exist "%HERE%libs\mc-named.jar" (
    echo libs\mc-named.jar is missing - create it with:  powershell -ExecutionPolicy Bypass -File tools\deobf.ps1 -NoSource
    pause
    exit /b 1
)
if not exist "%HERE%libs\onyx-deobf.jar" (
    echo libs\onyx-deobf.jar is missing - build it with:  gradlew.bat build
    pause
    exit /b 1
)

set "CP=%HERE%libs\onyx-deobf.jar;%HERE%libs\mc-named.jar"
for %%J in ("rt\libs\*.jar") do if /I not "%%~nxJ"=="mc.jar" set "CP=!CP!;%%~fJ"

set "JAVA=jre\bin\javaw.exe"
if /I "%~1"=="console" set "JAVA=jre\bin\java.exe"

"%JAVA%" -Xmx4G -Dfile.encoding=UTF-8 -Donyx.user="%ONYX_USER%" -Djava.library.path="rt\natives" -cp "!CP!" Start --username %MC_USERNAME% --gameDir "." --width 925 --height 530
