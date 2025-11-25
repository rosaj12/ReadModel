@echo off
echo.
echo ===================================
echo   COMPILAR ReadCore Android
echo ===================================
echo.

cd /d "%~dp0"

echo [1/4] Verificando ambiente...
if not exist "gradlew.bat" (
    echo ERRO: gradlew.bat nao encontrado!
    pause
    exit /b 1
)

echo [2/4] Limpando build anterior...
call gradlew.bat clean

echo [3/4] Compilando APK Debug...
call gradlew.bat assembleDebug

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===================================
    echo   COMPILACAO CONCLUIDA!
    echo ===================================
    echo.
    echo APK gerado em:
    echo app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo Para instalar:
    echo 1. Conecte o dispositivo
    echo 2. Execute: gradlew.bat installDebug
    echo.
) else (
    echo.
    echo ===================================
    echo   ERRO NA COMPILACAO!
    echo ===================================
    echo.
    echo Solucoes:
    echo 1. Use o Android Studio para sincronizar
    echo 2. Verifique se tem internet
    echo 3. Consulte COMO-COMPILAR.md
    echo.
)

pause
