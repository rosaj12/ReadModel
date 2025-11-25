# COMO COMPILAR - ReadCore Android

## 🎯 Passo a Passo Completo

### Pré-requisitos

1. **Instalar o Android Studio**
   - Baixe em: https://developer.android.com/studio
   - Versão mínima: Hedgehog (2023.1.1)
   - Durante a instalação, certifique-se de instalar:
     - Android SDK
     - Android SDK Platform (API 24-34)
     - Android SDK Build-Tools
     - Android Emulator

2. **Instalar o JDK**
   - JDK 17 ou superior
   - Opção 1: Usar o JDK incluído no Android Studio
   - Opção 2: Baixar do Oracle ou usar OpenJDK

3. **Configurar variáveis de ambiente** (Windows)
   ```
   JAVA_HOME = C:\Program Files\Android\Android Studio\jbr
   ANDROID_HOME = C:\Users\<SEU_USUARIO>\AppData\Local\Android\Sdk
   ```

### Método 1: Android Studio (Recomendado)

#### 1. Abrir o Projeto
```
1. Abra o Android Studio
2. File → Open
3. Navegue até: ReadCore\android
4. Clique em "OK"
5. Aguarde a sincronização do Gradle (pode levar alguns minutos)
```

#### 2. Configurar Dispositivo

**Opção A - Usar Emulador:**
```
1. Tools → Device Manager
2. Create Device
3. Selecione: Pixel 6
4. Download da imagem: API 33 (Tiramisu)
5. Clique em "Finish"
6. Inicie o emulador clicando no ▶️
```

**Opção B - Usar Dispositivo Real:**
```
1. No dispositivo Android:
   - Configurações → Sobre o telefone
   - Toque 7 vezes em "Número da versão"
   - Volte → Opções do desenvolvedor
   - Ative "Depuração USB"
2. Conecte via USB ao computador
3. Aceite a autorização no dispositivo
```

#### 3. Compilar e Executar
```
1. Build → Make Project (Ctrl+F9)
2. Run → Run 'app' (Shift+F10)
3. Selecione o dispositivo/emulador
4. Aguarde a instalação
```

O aplicativo será instalado e iniciado automaticamente!

### Método 2: Linha de Comando (Windows)

#### 1. Abrir PowerShell
```powershell
# Pressione Win+X e selecione "Windows PowerShell"
# Ou pressione Win+R, digite "powershell" e Enter
```

#### 2. Navegar até o projeto
```powershell
cd C:\Users\<SEU_USUARIO>\Desktop\ReadModel\ReadCore\android
```

#### 3. Compilar o APK
```powershell
# Compilar versão debug
.\gradlew.bat assembleDebug

# Aguarde... Deve aparecer "BUILD SUCCESSFUL"
```

#### 4. Verificar o APK gerado
```powershell
# Ver o arquivo gerado
dir app\build\outputs\apk\debug\

# Deve aparecer: app-debug.apk
```

#### 5. Instalar no dispositivo
```powershell
# Se tiver emulador rodando ou dispositivo conectado:
.\gradlew.bat installDebug

# Ou instalar manualmente com ADB:
adb install app\build\outputs\apk\debug\app-debug.apk
```

### Método 3: Linha de Comando (Linux/Mac)

```bash
# Abrir terminal
cd /caminho/para/ReadCore/android

# Dar permissão ao gradlew
chmod +x gradlew

# Compilar
./gradlew assembleDebug

# Instalar
./gradlew installDebug
```

## 🔧 Solução de Problemas Comuns

### Erro: "SDK location not found"

**Solução:**
1. Crie arquivo `local.properties` em `android/`
2. Adicione a linha:
   ```
   sdk.dir=C\:\\Users\\<SEU_USUARIO>\\AppData\\Local\\Android\\Sdk
   ```

### Erro: "JAVA_HOME is not set"

**Solução Windows:**
```powershell
# Definir temporariamente
$env:JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"

# Ou adicionar permanentemente:
# Painel de Controle → Sistema → Configurações Avançadas
# → Variáveis de Ambiente → Nova variável do sistema
# Nome: JAVA_HOME
# Valor: C:\Program Files\Android\Android Studio\jbr
```

### Erro: "Gradle sync failed"

**Solução:**
```powershell
# Limpar cache do Gradle
.\gradlew.bat clean
.\gradlew.bat --refresh-dependencies

# Se persistir, deletar pasta .gradle
Remove-Item -Recurse -Force .gradle
```

### Erro: "No devices found"

**Solução para emulador:**
```powershell
# Listar emuladores
emulator -list-avds

# Se vazio, criar um no Android Studio:
# Tools → Device Manager → Create Device
```

**Solução para dispositivo real:**
```powershell
# Verificar conexão
adb devices

# Se vazio:
# 1. Verificar cabo USB
# 2. Reativar "Depuração USB" no dispositivo
# 3. Tentar outro cabo/porta USB
# 4. Instalar drivers USB do fabricante

# Reiniciar servidor ADB
adb kill-server
adb start-server
adb devices
```

### Erro de memória durante build

**Solução:**
Edite `gradle.properties` e aumente a memória:
```properties
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
org.gradle.daemon=true
org.gradle.parallel=true
```

### Build muito lento

**Solução:**
```powershell
# Habilitar cache do Gradle
# Adicione em gradle.properties:
org.gradle.caching=true
org.gradle.parallel=true
org.gradle.configureondemand=true

# Build offline (se já baixou dependências)
.\gradlew.bat assembleDebug --offline
```

## 📝 Verificar Versões

### Verificar Java
```powershell
java -version
# Deve mostrar: openjdk version "17.x.x" ou superior
```

### Verificar Gradle
```powershell
.\gradlew.bat --version
# Deve mostrar: Gradle 8.0 ou superior
```

### Verificar Android SDK
```powershell
# Localização padrão Windows:
dir "C:\Users\<SEU_USUARIO>\AppData\Local\Android\Sdk"

# Deve ter pastas: platforms, build-tools, platform-tools
```

### Verificar ADB
```powershell
adb version
# Deve mostrar: Android Debug Bridge version X.X.X
```

## 🎓 Entendendo o Processo

### O que acontece durante o build?

1. **Gradle sync**: Baixa dependências (primeira vez: ~500MB)
2. **Compile**: Compila código Kotlin para bytecode
3. **Dex**: Converte bytecode para formato Android (DEX)
4. **Package**: Empacota código + recursos em APK
5. **Sign**: Assina o APK (debug keystore automática)
6. **Output**: Gera app-debug.apk

### Estrutura do APK gerado

```
app-debug.apk
├── AndroidManifest.xml    # Configurações do app
├── classes.dex            # Código compilado
├── resources.arsc         # Recursos compilados
├── res/                   # Imagens, strings, etc.
├── lib/                   # Bibliotecas nativas
└── META-INF/              # Assinaturas
```

## 📱 Instalar APK Manualmente

### No Emulador
```powershell
# Arraste o APK para a janela do emulador
# Ou use ADB:
adb install app\build\outputs\apk\debug\app-debug.apk
```

### No Dispositivo Real

**Método 1 - Via USB:**
```powershell
adb install app\build\outputs\apk\debug\app-debug.apk
```

**Método 2 - Copiar APK:**
```
1. Copie app-debug.apk para o dispositivo
2. Use um gerenciador de arquivos
3. Toque no APK
4. Permita "Instalar de fontes desconhecidas"
5. Toque em "Instalar"
```

**Método 3 - Via nuvem:**
```
1. Upload do APK para Google Drive/Dropbox
2. Baixe no dispositivo Android
3. Abra Downloads
4. Toque no APK
5. Instale
```

## ✅ Checklist de Compilação

- [ ] Android Studio instalado
- [ ] JDK 17+ instalado
- [ ] ANDROID_HOME configurado
- [ ] Projeto aberto e sincronizado
- [ ] Gradle sync bem-sucedido (sem erros vermelhos)
- [ ] Emulador criado OU dispositivo conectado
- [ ] Build executado sem erros
- [ ] APK gerado em `app/build/outputs/apk/debug/`
- [ ] App instalado e funcionando

## 🚀 Próximos Passos

Após compilar com sucesso:

1. **Testar funcionalidades**:
   - Adicionar livros
   - Abrir e ler
   - Criar marcadores
   - Testar busca

2. **Compilar versão Release**:
   - Seguir instruções em `APK-INFO.md`
   - Gerar keystore
   - Assinar APK

3. **Personalizar**:
   - Modificar cores em `theme/Color.kt`
   - Alterar ícone em `res/mipmap/`
   - Adicionar funcionalidades

## 📚 Recursos Adicionais

- **Android Developers**: https://developer.android.com
- **Gradle Docs**: https://docs.gradle.org
- **Kotlin Docs**: https://kotlinlang.org/docs/home.html
- **Jetpack Compose**: https://developer.android.com/jetpack/compose

## 💡 Dicas Finais

1. **Primeira compilação é lenta**: Gradle baixa dependências (~500MB)
2. **Compilações seguintes são rápidas**: Gradle usa cache
3. **Use build incremental**: Não execute "Clean" sem necessidade
4. **Mantenha Android Studio atualizado**: Melhora performance
5. **Use SSD**: Muito mais rápido que HD
6. **Feche apps pesados**: Durante build para liberar RAM

---

**Precisa de ajuda?**  
Consulte os logs de erro completos e procure a mensagem no Stack Overflow ou na documentação oficial do Android.
