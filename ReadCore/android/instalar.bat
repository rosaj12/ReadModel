@echo off
echo.
echo ===================================
echo   INSTALAR ReadCore Android
echo ===================================
echo.

cd /d "%~dp0"

echo Verificando dispositivo conectado...
adb devices

echo.
echo Instalando APK...
call gradlew.bat installDebug

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===================================
    echo   APP INSTALADO COM SUCESSO!
    echo ===================================
    echo.
    echo O app ReadCore foi instalado no dispositivo.
    echo Abra-o manualmente ou execute:
    echo adb shell am start -n com.readcore.android/.framework.MainActivity
    echo.
) else (
    echo.
    echo ===================================
    echo   ERRO NA INSTALACAO!
    echo ===================================
    echo.
    echo Verifique:
    echo 1. Dispositivo conectado (adb devices)
    echo 2. Depuracao USB habilitada
    echo 3. APK compilado (execute compilar.bat primeiro)
    echo.
)

pause
