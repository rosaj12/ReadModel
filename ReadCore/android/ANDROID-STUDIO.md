# 🎯 CONFIGURAR E EXECUTAR NO ANDROID STUDIO

## 📋 Guia Passo a Passo Completo

---

## PARTE 1: INSTALAR ANDROID STUDIO

### Passo 1: Download

1. Acesse: **https://developer.android.com/studio**
2. Clique em **"Download Android Studio"**
3. Aceite os termos e baixe (~1 GB)

### Passo 2: Instalação

#### Windows:

1. Execute o arquivo baixado: `android-studio-xxx.exe`
2. Clique **Next** na tela de boas-vindas
3. Selecione componentes (deixe tudo marcado):
   - ✅ Android Studio
   - ✅ Android Virtual Device (emulador)
4. **Next** → Escolha local de instalação (padrão: `C:\Program Files\Android\Android Studio`)
5. **Next** → Nome do menu iniciar → **Install**
6. Aguarde instalação (~5 minutos)
7. **Finish**

### Passo 3: Primeira Configuração (Setup Wizard)

1. Android Studio abrirá o **Setup Wizard**
2. Tela de boas-vindas → **Next**

3. **Install Type:**
   - Selecione: **Standard** (recomendado)
   - **Next**

4. **Select UI Theme:**
   - Escolha: **Darcula** (tema escuro) ou **Light** (tema claro)
   - **Next**

5. **Verify Settings:**
   - Verifique componentes que serão instalados:
     ```
     ✓ Android SDK
     ✓ Android SDK Platform (API 34)
     ✓ Android SDK Build-Tools
     ✓ Android SDK Platform-Tools
     ✓ Android Emulator
     ✓ Android SDK Command-line Tools
     ```
   - **Next**

6. **License Agreement:**
   - Selecione cada licença
   - Clique **Accept** para cada uma
   - **Finish**

7. **Downloading Components:**
   - Aguarde download (~2-3 GB, pode demorar 10-30 minutos)
   - Barra de progresso mostrará o andamento
   - **Finish** quando concluir

8. Android Studio está pronto! Tela inicial aparecerá

---

## PARTE 2: ABRIR O PROJETO READCORE

### Passo 1: Abrir Projeto

Na tela inicial do Android Studio:

```
┌─────────────────────────────────────┐
│   Android Studio                    │
├─────────────────────────────────────┤
│                                     │
│   New Project                       │
│   Open                          ◄── CLIQUE AQUI
│   Get from VCS                      │
│                                     │
└─────────────────────────────────────┘
```

1. Clique em **"Open"**
2. Navegue até: `C:\Users\mikan\Desktop\ReadModel\ReadCore\android`
3. Selecione a pasta **`android`**
4. Clique **OK**

### Passo 2: Aguardar Gradle Sync

Após abrir, você verá:

```
┌─────────────────────────────────────────────────────┐
│  🔄 Gradle Sync                                     │
├─────────────────────────────────────────────────────┤
│  Downloading gradle-wrapper.jar...                  │
│  Downloading dependencies...                        │
│  Building project...                                │
│                                                     │
│  [████████████░░░░░░░░░] 60%                       │
└─────────────────────────────────────────────────────┘
```

**O que está acontecendo:**
1. ⏳ Baixando Gradle 8.0 (~100 MB)
2. ⏳ Baixando dependências do projeto:
   - PDFBox Android (~8 MB)
   - Jetpack Compose (~200 MB)
   - Kotlin libraries (~150 MB)
   - Outras dependências (~100 MB)
3. ⏳ Compilando configurações
4. ⏳ Indexando arquivos

**Tempo estimado:** 5-15 minutos na primeira vez

**Status na barra inferior:**
- Durante: `Gradle build running...`
- Concluído: ✅ `Gradle sync finished in 8m 23s`

### Passo 3: Verificar Sync Completo

Quando terminar, você verá:

**Barra superior (verde):**
```
✅ Gradle sync finished successfully
```

**Estrutura do projeto (lateral esquerda):**
```
📁 android
├── 📁 app
│   ├── 📁 src
│   │   ├── 📁 main
│   │   │   ├── 📁 java/com/readcore/android
│   │   │   │   ├── 📁 domain
│   │   │   │   ├── 📁 usecases
│   │   │   │   ├── 📁 adapters
│   │   │   │   └── 📁 framework
│   │   │   ├── 📁 res
│   │   │   └── AndroidManifest.xml
│   │   └── 📁 test
│   └── build.gradle.kts
├── 📁 gradle
└── build.gradle.kts
```

---

## PARTE 3: CONFIGURAR DISPOSITIVO

Você precisa de um dispositivo para executar o app. Escolha uma opção:

---

### OPÇÃO A: EMULADOR (Simulador Android)

#### Passo 1: Abrir Device Manager

```
Menu superior: Tools → Device Manager
```

Ou clique no ícone 📱 na barra de ferramentas superior

#### Passo 2: Criar Emulador

Na janela **Device Manager**:

1. Clique em **"Create Device"** (ou ícone +)

2. **Select Hardware:**
   ```
   Category: Phone
   Device: Pixel 6  ◄── RECOMENDADO
   ```
   - Outros bons: Pixel 7, Pixel 5
   - **Next**

3. **System Image:**
   ```
   Release Name    API Level    Target
   ────────────────────────────────────
   Tiramisu        33          Android 13  ◄── RECOMENDADO
   UpsideDownCake  34          Android 14  ◄── Também OK
   ```
   
   - Se aparecer **"Download"** ao lado → Clique para baixar (~1 GB)
   - Aguarde download
   - Selecione a imagem
   - **Next**

4. **Verify Configuration:**
   ```
   AVD Name: Pixel_6_API_33
   Startup orientation: Portrait
   
   Emulated Performance:
   Graphics: Automatic (ou Hardware se tiver GPU)
   
   Memory:
   RAM: 2048 MB (pode aumentar se tiver RAM sobrando)
   ```
   - **Finish**

#### Passo 3: Iniciar Emulador

Na lista de dispositivos:

1. Encontre seu emulador: `Pixel_6_API_33`
2. Clique no ícone **▶️ (Play)**
3. Aguarde inicialização (~1-2 minutos primeira vez)
4. Emulador abrirá em nova janela

**Você verá a tela inicial do Android simulado!**

---

### OPÇÃO B: CELULAR REAL (Mais Rápido)

#### Passo 1: Habilitar Modo Desenvolvedor

**No seu celular Android:**

1. **Abra Configurações**
2. **Sobre o telefone** (ou **Sistema** → **Sobre**)
3. **Toque 7 vezes** em "Número da versão" ou "Número de build"
   - Aparecerá: "Você agora é um desenvolvedor!"

#### Passo 2: Ativar Depuração USB

1. **Volte** para Configurações
2. **Opções do desenvolvedor** (pode estar em Sistema → Avançado)
3. **Ative "Opções do desenvolvedor"** (toggle no topo)
4. **Ative "Depuração USB"**
5. Se aparecer "Permitir depuração USB?", marque **Sempre permitir** e toque **OK**

#### Passo 3: Conectar ao PC

1. **Conecte o celular ao PC via cabo USB**
2. **No celular**, aparecerá uma notificação:
   ```
   USB para transferência de arquivos
   Toque para mais opções
   ```
   - Pode deixar em qualquer modo (MTP/PTP)

3. **Autorização de depuração:**
   ```
   ┌────────────────────────────────────┐
   │  Permitir depuração USB?           │
   ├────────────────────────────────────┤
   │  Impressão digital RSA:            │
   │  XX:XX:XX:XX...                    │
   │                                    │
   │  ☑ Sempre permitir deste computador│
   │                                    │
   │  [CANCELAR]  [OK]              ◄── CLIQUE
   └────────────────────────────────────┘
   ```
   - Marque ☑ **Sempre permitir**
   - Toque **OK**

#### Passo 4: Verificar Conexão

**No Android Studio:**

Barra superior mostrará seu dispositivo:
```
┌────────────────────────────────┐
│  Samsung Galaxy A52 (Android 13) │  ◄── Seu celular
└────────────────────────────────┘
```

**Ou verificar manualmente:**

No Android Studio:
1. **View → Tool Windows → Logcat**
2. No menu suspenso, você verá seu dispositivo listado

**Ou via terminal integrado:**

No Android Studio:
1. **View → Tool Windows → Terminal**
2. Digite: `adb devices`
3. Deve aparecer:
   ```
   List of devices attached
   ABC123XYZ    device
   ```

---

## PARTE 4: EXECUTAR O APP

### Método 1: Botão Run (Mais Fácil)

**Barra superior do Android Studio:**

```
┌──────────────────────────────────────────────────┐
│  [app ▼] [Pixel_6_API_33 ▼] [▶️ Run] [🐛 Debug] │
└──────────────────────────────────────────────────┘
      ↑            ↑              ↑
   Módulo    Dispositivo    CLIQUE AQUI
```

1. **Verifique** se o dispositivo correto está selecionado
2. **Clique no botão verde ▶️ "Run"**
3. Ou pressione: **Shift + F10**

### Método 2: Menu

```
Menu: Run → Run 'app'
Atalho: Shift + F10
```

### O que Acontece:

**Console de build (parte inferior):**

```
┌─────────────────────────────────────────────────┐
│  Build                                          │
├─────────────────────────────────────────────────┤
│  > Task :app:preBuild                           │
│  > Task :app:compileDebugKotlin                 │
│  > Task :app:compileDebugJavaWithJavac          │
│  > Task :app:mergeDebugResources                │
│  > Task :app:packageDebug                       │
│  > Task :app:assembleDebug                      │
│                                                 │
│  BUILD SUCCESSFUL in 2m 15s                     │
│  142 actionable tasks: 142 executed             │
└─────────────────────────────────────────────────┘
```

**Progresso:**
1. ⏳ Building (1-3 minutos primeira vez)
2. ⏳ Installing APK on device
3. ⏳ Launching activity
4. ✅ **App abre no dispositivo/emulador!**

**Run tab (parte inferior):**
```
┌─────────────────────────────────────────────────┐
│  Run                                            │
├─────────────────────────────────────────────────┤
│  Launching 'app' on Pixel 6 API 33.             │
│  Install successfully finished in 3 s 241 ms.   │
│  $ adb shell am start -n "com.readcore.android/.│
│    framework.MainActivity"                      │
│  Starting: Intent { cmp=com.readcore.android/.  │
│    framework.MainActivity }                     │
└─────────────────────────────────────────────────┘
```

### Primeira Tela do App:

O app abrirá mostrando:

```
┌──────────────────────────────────┐
│  ←  Biblioteca de Livros    🔍   │
├──────────────────────────────────┤
│                                  │
│         📚                       │
│                                  │
│    Biblioteca vazia              │
│                                  │
│  Adicione livros tocando no      │
│  botão +                         │
│                                  │
│                                  │
│                                  │
│                          [+]  ◄──│ Botão adicionar
└──────────────────────────────────┘
```

---

## PARTE 5: TESTAR O APP

### Adicionar um Livro de Teste

#### No Emulador:

**1. Enviar arquivo para o emulador:**

No Android Studio:
```
View → Tool Windows → Terminal
```

Digite:
```bash
adb push "C:\caminho\para\seu\livro.pdf" /sdcard/Download/
```

Exemplo:
```bash
adb push "C:\Users\mikan\Downloads\livro.pdf" /sdcard/Download/
```

**2. No app:**
1. Toque no botão **+** (adicionar)
2. Permita acesso aos arquivos (popup de permissão)
3. Navegue até **Download**
4. Selecione o arquivo PDF/EPUB
5. Livro aparecerá na biblioteca!

#### No Celular Real:

**Opção 1 - Baixar da internet:**
1. No celular, baixe um PDF qualquer
2. Abra o app ReadCore
3. Toque em **+**
4. Vá até **Download**
5. Selecione o arquivo

**Opção 2 - Copiar do PC:**
1. Conecte celular via USB
2. Copie PDFs para a pasta **Download** do celular
3. No app, toque **+** e selecione

### Testar Leitura:

1. **Toque no livro** da lista
2. App abrirá a tela de leitura
3. **Navegue:** use as setas **<** **>**
4. **Ajustar fonte:** botões **A-** **A+**
5. **Marcador:** toque no ícone de marcador
6. **Ir para página:** ícone de páginas

---

## PARTE 6: FERRAMENTAS ÚTEIS DO ANDROID STUDIO

### 1. Logcat (Ver Logs do App)

```
View → Tool Windows → Logcat
```

Mostra logs em tempo real:
```
2025-11-25 10:30:15.123 D/ReadCore: Book added: livro.pdf
2025-11-25 10:30:20.456 I/ReadCore: Opening book ID: abc123
```

Filtrar logs do ReadCore:
```
Filtro: package:com.readcore.android
```

### 2. Device File Explorer

```
View → Tool Windows → Device File Explorer
```

Navegar arquivos do dispositivo:
```
📁 sdcard
  ├── 📁 Download       ◄── Seus PDFs aqui
  ├── 📁 Documents
  └── 📁 DCIM
```

Você pode:
- Arrastar arquivos para o dispositivo
- Baixar arquivos do dispositivo
- Ver logs do app

### 3. App Inspection

```
View → Tool Windows → App Inspection
```

Ver dados do DataStore:
```
📁 Preferences DataStore
  ├── books.preferences_pb
  ├── bookmarks.preferences_pb
  └── reading_progress.preferences_pb
```

### 4. Profiler

```
View → Tool Windows → Profiler
```

Monitorar performance:
- CPU usage
- Memory usage
- Network activity

### 5. Terminal Integrado

```
View → Tool Windows → Terminal
```

Executar comandos:
```bash
# Ver dispositivos
adb devices

# Ver logs
adb logcat | findstr ReadCore

# Enviar arquivo
adb push arquivo.pdf /sdcard/Download/

# Desinstalar app
adb uninstall com.readcore.android
```

---

## SOLUÇÃO DE PROBLEMAS

### ❌ Gradle Sync Failed

**Sintoma:** Barra vermelha no topo com erro

**Solução 1 - Invalidar Cache:**
```
File → Invalidate Caches → Invalidate and Restart
```

**Solução 2 - Reimportar:**
```
File → Close Project
→ Reabrir o projeto
→ Aguardar novo sync
```

**Solução 3 - Limpar Gradle:**
```
Terminal no Android Studio:
.\gradlew clean
```

---

### ❌ Dispositivo Não Detectado (Emulador)

**Solução:**
```
1. Device Manager → Parar emulador
2. Cold Boot → Iniciar novamente
3. Aguardar carregar completamente
```

---

### ❌ Dispositivo Não Detectado (Celular)

**Solução 1 - Reiniciar ADB:**
```
Terminal no Android Studio:
adb kill-server
adb start-server
adb devices
```

**Solução 2 - Verificar USB:**
- Trocar cabo USB
- Trocar porta USB do PC
- Desativar/reativar Depuração USB

**Solução 3 - Drivers:**
- Windows pode precisar de drivers do fabricante
- Samsung: Samsung USB Driver
- Xiaomi: Mi PC Suite
- Motorola: Motorola Device Manager

---

### ❌ Build Failed

**Ver erro completo:**
```
Build → Build Output (aba inferior)
```

**Soluções comuns:**

1. **Falta internet:**
   - Gradle precisa baixar dependências
   - Conecte à internet

2. **SDK não instalado:**
   ```
   Tools → SDK Manager
   → SDK Platforms → Marcar API 33 e 34
   → SDK Tools → Marcar Build-Tools 34.0.0
   → Apply
   ```

3. **JDK incorreto:**
   ```
   File → Project Structure → SDK Location
   → JDK location: Usar JDK do Android Studio
   ```

---

### ❌ App Crasha ao Abrir

**Ver logs:**
```
Logcat → Filtrar por "AndroidRuntime"
```

**Solução:**
1. Verificar permissões concedidas
2. Reinstalar app (Run novamente)
3. Verificar compatibilidade API (mínimo API 24)

---

## ATALHOS ÚTEIS

| Ação | Windows/Linux | Mac |
|------|---------------|-----|
| **Run** | Shift + F10 | Control + R |
| **Debug** | Shift + F9 | Control + D |
| **Build** | Ctrl + F9 | Cmd + F9 |
| **Clean** | - | - |
| **Sync Gradle** | - | - |
| **Find** | Ctrl + F | Cmd + F |
| **Replace** | Ctrl + R | Cmd + R |
| **Go to file** | Ctrl + Shift + N | Cmd + Shift + O |
| **Recent files** | Ctrl + E | Cmd + E |
| **Terminal** | Alt + F12 | Option + F12 |

---

## CONFIGURAÇÕES RECOMENDADAS

### 1. Aumentar Memória do Gradle

```
File → Settings → Build, Execution, Deployment → Compiler
→ Build process heap size: 2048 MB (ou mais se tiver RAM)
```

### 2. Ativar Auto-Import

```
File → Settings → Editor → General → Auto Import
→ ☑ Add unambiguous imports on the fly
→ ☑ Optimize imports on the fly
```

### 3. Formatação de Código

```
File → Settings → Editor → Code Style → Kotlin
→ Set from → Kotlin Style Guide
```

### 4. Live Templates (Snippets)

```
File → Settings → Editor → Live Templates
→ Kotlin → Você verá atalhos úteis
```

Exemplos:
- `fun` + Tab = criar função
- `main` + Tab = criar main
- `sout` + Tab = System.out.println

---

## CHECKLIST FINAL

Antes de começar a desenvolver:

- [ ] Android Studio instalado e configurado
- [ ] Projeto aberto e Gradle sync completo (sem erros)
- [ ] Emulador criado OU celular conectado
- [ ] App executado com sucesso (botão Run ▶️)
- [ ] Conseguiu adicionar e ler um livro de teste
- [ ] Logcat mostrando logs do app
- [ ] Nenhum erro vermelho no código

---

## PRÓXIMOS PASSOS

Agora que está configurado:

1. **Explorar código:**
   - `domain/` - Entidades
   - `usecases/` - Lógica de negócio
   - `framework/ui/screens/` - Telas Compose

2. **Fazer modificações:**
   - Mudar cores: `framework/ui/theme/Color.kt`
   - Adicionar features: Criar novos use cases
   - Melhorar UI: Editar `LibraryScreen.kt` ou `ReaderScreen.kt`

3. **Testar mudanças:**
   - Edite o código
   - Clique Run ▶️
   - Veja mudanças no dispositivo

4. **Debug:**
   - Adicione breakpoints (clique na margem esquerda do código)
   - Run em modo Debug (🐛)
   - Inspecione variáveis

---

## 📚 RECURSOS

- **Documentação Android:** https://developer.android.com
- **Jetpack Compose:** https://developer.android.com/jetpack/compose
- **Kotlin:** https://kotlinlang.org
- **Arquitetura do projeto:** Veja `ARQUITETURA.md`

---

**✅ PRONTO! Você está com tudo configurado para desenvolver no Android Studio!**

Qualquer dúvida, consulte a documentação ou veja os logs no Logcat.
