@echo off
echo Building ReadCore...
call mvn clean package

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Build successful! Running ReadCore...
    echo.
    java -jar target\readcore-1.0.0.jar
) else (
    echo.
    echo Build failed. Please check the errors above.
    pause
)
