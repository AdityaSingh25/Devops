@echo off
setlocal

REM Compile Java source
javac Demo.java
if errorlevel 1 (
    echo Compilation failed.
    exit /b 1
)

REM Create a runnable JAR using the manifest
jar cfm Demo.jar manifest.txt *.class
if errorlevel 1 (
    echo Jar creation failed.
    exit /b 1
)

echo Created Demo.jar
endlocal
