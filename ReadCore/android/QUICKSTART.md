# GUIA RÁPIDO - ReadCore Android

## Compilar e Instalar

### Windows (PowerShell):

```powershell
# Navegar para o diretório do projeto Android
cd ReadCore\android

# Compilar o APK de debug
.\gradlew.bat assembleDebug

# Instalar no dispositivo/emulador conectado
.\gradlew.bat installDebug
```

### Linux/Mac:

```bash
# Navegar para o diretório do projeto Android
cd ReadCore/android

# Compilar o APK de debug
./gradlew assembleDebug

# Instalar no dispositivo/emulador conectado
./gradlew installDebug
```

## Localização dos APKs

Após compilar, os APKs estarão em:
- **Debug**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release**: `app/build/outputs/apk/release/app-release.apk`

## Pré-requisitos

1. **Android Studio**: Hedgehog (2023.1.1) ou superior
2. **JDK**: 17 ou superior
3. **Android SDK**: API 24-34
4. **Gradle**: 8.0+ (incluído no wrapper)

## Primeira Execução

1. Abra o Android Studio
2. File → Open → Selecione a pasta `ReadCore/android`
3. Aguarde a sincronização do Gradle
4. Conecte um dispositivo Android ou inicie um emulador
5. Clique em "Run" (▶️) ou pressione Shift+F10

## Emulador Android

### Criar emulador via Android Studio:
1. Tools → Device Manager
2. Create Device
3. Selecione um dispositivo (ex: Pixel 6)
4. Escolha uma imagem do sistema (API 33 recomendada)
5. Finish

### Via linha de comando:
```bash
# Listar emuladores disponíveis
emulator -list-avds

# Iniciar um emulador
emulator -avd <nome_do_emulador>
```

## Testando a aplicação

### Adicionar arquivos de teste ao emulador:
1. Abra o Device File Explorer no Android Studio
2. Navegue para `/sdcard/Download/`
3. Arraste arquivos PDF, EPUB ou TXT para esta pasta
4. No app, toque em + e selecione os arquivos

### Ou via ADB:
```bash
# Enviar arquivo para o emulador
adb push livro.pdf /sdcard/Download/

# Verificar arquivos
adb shell ls /sdcard/Download/
```

## Comandos úteis

```bash
# Limpar build
.\gradlew.bat clean

# Compilar e instalar
.\gradlew.bat installDebug

# Desinstalar
adb uninstall com.readcore.android

# Ver logs
adb logcat | findstr ReadCore

# Listar dispositivos conectados
adb devices

# Build de release
.\gradlew.bat assembleRelease
```

## Solução de problemas

### Gradle sync falhou:
```bash
# Limpar cache do Gradle
.\gradlew.bat clean --refresh-dependencies
```

### Dispositivo não detectado:
```bash
# Reiniciar servidor ADB
adb kill-server
adb start-server
adb devices
```

### Erro de memória durante build:
Edite `gradle.properties` e aumente:
```
org.gradle.jvmargs=-Xmx4096m
```

### APK não instala:
- Habilite "Fontes desconhecidas" nas configurações
- Desinstale versão anterior primeiro
- Verifique espaço disponível no dispositivo

## Estrutura do projeto

```
android/
├── app/
│   ├── src/main/
│   │   ├── java/              # Código Kotlin
│   │   ├── res/               # Recursos (layouts, strings, etc)
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts       # Configuração do módulo app
├── gradle/                     # Wrapper do Gradle
├── build.gradle.kts           # Configuração raiz
└── settings.gradle.kts        # Configuração do projeto
```

## Funcionalidades implementadas

✅ Biblioteca de livros com busca  
✅ Suporte a PDF, EPUB, TXT, MOBI  
✅ Leitor com navegação de páginas  
✅ Marcadores com anotações  
✅ Progresso de leitura automático  
✅ Ajuste de tamanho de fonte  
✅ Armazenamento offline (DataStore)  
✅ Material Design 3  
✅ Dark/Light theme automático  

## Próximos passos

Para desenvolver mais:
1. Adicione testes unitários em `app/src/test/`
2. Adicione testes instrumentados em `app/src/androidTest/`
3. Configure CI/CD para builds automatizados
4. Implemente sincronização na nuvem
5. Adicione compartilhamento de citações
6. Implemente modo noturno personalizado

## Suporte

Para dúvidas sobre:
- **Gradle**: https://gradle.org/docs/
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Android Studio**: https://developer.android.com/studio/intro
