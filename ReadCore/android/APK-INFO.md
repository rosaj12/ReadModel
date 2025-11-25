# APK INFO - ReadCore Android

## 📦 Informações do APK

### Compilação

**Debug APK:**
- Localização: `app/build/outputs/apk/debug/app-debug.apk`
- Comando: `.\gradlew.bat assembleDebug`
- Assinatura: Debug keystore automática
- Tamanho estimado: ~15-20 MB
- Minificado: Não
- Ofuscado: Não

**Release APK:**
- Localização: `app/build/outputs/apk/release/app-release.apk`
- Comando: `.\gradlew.bat assembleRelease`
- Assinatura: Requer configuração de keystore
- Tamanho estimado: ~10-15 MB (com ProGuard)
- Minificado: Configurável
- Ofuscado: Configurável

### Requisitos do Dispositivo

- **Android mínimo**: API 24 (Android 7.0 Nougat)
- **Android alvo**: API 34 (Android 14)
- **Arquitetura**: Todas (armeabi-v7a, arm64-v8a, x86, x86_64)
- **Permissões**:
  - `READ_EXTERNAL_STORAGE` (Android ≤ 12)
  - `READ_MEDIA_DOCUMENTS` (Android ≥ 13)

### Dependências Incluídas no APK

1. **AndroidX Core** (~2 MB)
   - Core KTX 1.12.0
   - Lifecycle Runtime 2.6.2
   - Activity Compose 1.8.0

2. **Jetpack Compose** (~5-7 MB)
   - UI, Material3, Navigation
   - Compose BOM 2023.10.01

3. **PDFBox Android** (~8 MB)
   - Versão: 2.0.27.0
   - Maior dependência do APK

4. **JSoup** (~400 KB)
   - Versão: 1.16.1
   - Parsing de HTML/EPUB

5. **Gson** (~250 KB)
   - Versão: 2.10.1
   - Serialização JSON

6. **Coroutines** (~1 MB)
   - Versão: 1.7.3
   - Programação assíncrona

7. **DataStore** (~200 KB)
   - Versão: 1.0.0
   - Persistência de dados

**Tamanho total estimado**: 15-20 MB (Debug), 10-15 MB (Release)

## 🔨 Como Compilar

### 1. Compilar Debug APK

```powershell
# Windows
cd ReadCore\android
.\gradlew.bat assembleDebug
```

```bash
# Linux/Mac
cd ReadCore/android
./gradlew assembleDebug
```

O APK será gerado em:
```
app/build/outputs/apk/debug/app-debug.apk
```

### 2. Compilar Release APK

Primeiro, configure o keystore (uma única vez):

```powershell
# Criar keystore
keytool -genkey -v -keystore readcore-release.keystore -alias readcore -keyalg RSA -keysize 2048 -validity 10000
```

Adicione ao `app/build.gradle.kts`:

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("../readcore-release.keystore")
            storePassword = "sua_senha"
            keyAlias = "readcore"
            keyPassword = "sua_senha"
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

Depois compile:

```powershell
.\gradlew.bat assembleRelease
```

O APK será gerado em:
```
app/build/outputs/apk/release/app-release.apk
```

## 📲 Instalação

### Via Android Studio
1. Conecte o dispositivo ou inicie o emulador
2. Run → Run 'app'

### Via Gradle
```powershell
.\gradlew.bat installDebug
```

### Via ADB (Manual)
```powershell
adb install app\build\outputs\apk\debug\app-debug.apk
```

### Via Arquivo APK
1. Copie o APK para o dispositivo
2. Abra o gerenciador de arquivos
3. Toque no APK
4. Permita "Fontes desconhecidas" se necessário
5. Toque em "Instalar"

## 🧪 Testando o APK

### No Emulador
```powershell
# Listar emuladores
emulator -list-avds

# Iniciar emulador
emulator -avd Pixel_6_API_33

# Instalar APK
adb install app\build\outputs\apk\debug\app-debug.apk

# Adicionar arquivo de teste
adb push livro.pdf /sdcard/Download/
```

### No Dispositivo Real
1. Habilite "Depuração USB" nas opções de desenvolvedor
2. Conecte o dispositivo via USB
3. Execute `adb devices` para verificar conexão
4. Execute `.\gradlew.bat installDebug`

## 📊 Análise do APK

### Ver tamanho e conteúdo
```powershell
# Analisar APK no Android Studio
Build → Analyze APK → Selecione o app-debug.apk
```

### Via linha de comando
```powershell
# Ver tamanho do APK
dir app\build\outputs\apk\debug\app-debug.apk

# Extrair conteúdo
unzip app\build\outputs\apk\debug\app-debug.apk -d apk-contents

# Ver classes DEX
dexdump app\build\outputs\apk\debug\app-debug.apk
```

## 🔍 Reduzindo Tamanho do APK

### 1. Ativar ProGuard/R8 (Release)
```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
    }
}
```

### 2. Dividir APKs por arquitetura
```kotlin
android {
    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
            isUniversalApk = false
        }
    }
}
```

### 3. Usar App Bundle
```powershell
.\gradlew.bat bundleRelease
```
Gera: `app/build/outputs/bundle/release/app-release.aab`

## 🚀 Distribuição

### Google Play Store
1. Compile o App Bundle (AAB)
2. Faça login no Google Play Console
3. Crie um novo aplicativo
4. Faça upload do AAB
5. Preencha informações e screenshots
6. Publique

### Distribuição Direta
1. Compile o APK Release assinado
2. Hospede em servidor web ou compartilhe diretamente
3. Usuários devem permitir "Fontes desconhecidas"

### F-Droid ou Outras Lojas
1. Siga as diretrizes específicas de cada loja
2. Geralmente requerem código-fonte aberto
3. Build automático pelos servidores da loja

## ⚙️ Configurações Avançadas

### Build com diferentes flavors
```kotlin
android {
    flavorDimensions += "version"
    productFlavors {
        create("free") {
            dimension = "version"
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"
        }
        create("pro") {
            dimension = "version"
            applicationIdSuffix = ".pro"
            versionNameSuffix = "-pro"
        }
    }
}
```

### Build multi-APK
```powershell
# Gerar todos os APKs
.\gradlew.bat assembleDebug

# APKs gerados:
# - app-armeabi-v7a-debug.apk
# - app-arm64-v8a-debug.apk
# - app-x86-debug.apk
# - app-x86_64-debug.apk
```

## 📝 Versionamento

Atualize em `app/build.gradle.kts`:

```kotlin
android {
    defaultConfig {
        versionCode = 2  // Incrementar a cada release
        versionName = "1.1"  // Versão visível ao usuário
    }
}
```

## 🔐 Segurança

### Ofuscar código
Adicione regras em `proguard-rules.pro`:

```proguard
# Manter classes de domínio
-keep class com.readcore.android.domain.** { *; }

# Manter modelos JSON
-keep class com.readcore.android.domain.entities.** { *; }

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }
```

### Assinar APK com segurança
Nunca commite senhas! Use variáveis de ambiente:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file(System.getenv("KEYSTORE_FILE") ?: "keystore.jks")
        storePassword = System.getenv("KEYSTORE_PASSWORD")
        keyAlias = System.getenv("KEY_ALIAS")
        keyPassword = System.getenv("KEY_PASSWORD")
    }
}
```

## 🐛 Debugging

### Ver logs do app
```powershell
adb logcat | findstr "ReadCore"
```

### Debug via WiFi
```powershell
# Conectar via USB primeiro
adb tcpip 5555
# Descubra o IP do dispositivo
adb shell ip addr show wlan0
# Conecte via WiFi
adb connect <IP>:5555
```

## 📈 Métricas

Após build, verifique:
- **Tamanho do APK**: Deve estar entre 10-20 MB
- **Métodos count**: Verifique se não excede 64K (sem multidex)
- **Dependências**: Revise regularmente para remover não utilizadas
- **Build time**: Otimize com Gradle daemon e cache

## ✅ Checklist de Release

- [ ] Atualizar versionCode e versionName
- [ ] Testar em múltiplos dispositivos/APIs
- [ ] Revisar permissões no AndroidManifest
- [ ] Executar lint: `.\gradlew.bat lint`
- [ ] Executar testes: `.\gradlew.bat test`
- [ ] Ativar ProGuard/R8
- [ ] Assinar com keystore de produção
- [ ] Gerar APK/AAB de release
- [ ] Testar APK de release em dispositivo real
- [ ] Preparar release notes
- [ ] Criar tag no Git
- [ ] Upload para loja ou distribuição
