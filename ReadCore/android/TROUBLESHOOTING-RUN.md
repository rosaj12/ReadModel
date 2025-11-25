# 🔧 SOLUÇÃO: Botão Run não funciona no Android Studio

## 🔍 DIAGNÓSTICO RÁPIDO

Verifique primeiro qual é o seu problema exato:

---

## ❌ PROBLEMA 1: Botão Run está DESABILITADO (cinza)

### Sintoma:
```
[app ▼] [No devices ▼] [▶️] ◄── Botão cinza, não clicável
```

### Causas comuns:

#### ✅ SOLUÇÃO A: Gradle Sync não completou

**Verificar:**
- Olhe a barra inferior do Android Studio
- Se aparecer: `Gradle build running...` ou `Syncing...`
- **Aguarde até aparecer:** `Gradle sync finished`

**Forçar sync:**
```
File → Sync Project with Gradle Files
```
Ou clique no ícone 🐘 (elefante) na barra superior

**Tempo:** 5-15 minutos na primeira vez

**Se sync falhar com ERRO:**
```
Tools → Gradle → Refresh Gradle Dependencies
```

---

#### ✅ SOLUÇÃO B: Nenhum dispositivo configurado

**Verificar:**
No menu suspenso superior, vê:
```
[app ▼] [No devices ▼] ◄── PROBLEMA
```

**Solução - Criar emulador:**
```
1. Tools → Device Manager
2. Create Device
3. Escolha: Pixel 6
4. Download: API 33 (Tiramisu)
5. Finish
6. Clique ▶️ para iniciar o emulador
```

**Solução - Conectar celular:**
```
1. No celular: Habilitar Depuração USB
2. Conectar via USB
3. Aceitar autorização no celular
4. Aguardar aparecer no Android Studio
```

**Verificar conexão:**
```
View → Tool Windows → Terminal
Digite: adb devices

Deve mostrar:
List of devices attached
ABC123XYZ    device  ◄── Seu dispositivo
```

Se aparecer `unauthorized`:
- Desconecte e reconecte o USB
- No celular: Revogar autorizações → Aceitar novamente

---

#### ✅ SOLUÇÃO C: Projeto não carregou corretamente

**Sintomas:**
- Arquivos com ícone vermelho
- Pastas não expandem
- Estrutura estranha

**Solução:**
```
1. File → Close Project
2. Na tela inicial: Open
3. Navegue até: ReadCore\android
4. Selecione a pasta ANDROID (não a raiz ReadCore)
5. OK
6. Aguarde Gradle sync completo
```

**Se ainda não funcionar:**
```
File → Invalidate Caches → Invalidate and Restart
```

---

#### ✅ SOLUÇÃO D: Configuração de Run ausente

**Verificar:**
Menu superior mostra:
```
[Add Configuration... ▼] ◄── PROBLEMA
```

**Solução:**
```
1. Run → Edit Configurations
2. Clique no + (topo esquerdo)
3. Selecione: Android App
4. Name: app
5. Module: android.app.main
6. OK
```

Agora deve aparecer:
```
[app ▼] [Pixel_6 ▼] [▶️]
```

---

## ❌ PROBLEMA 2: Botão Run clicável mas NÃO EXECUTA

### ✅ SOLUÇÃO A: Gradle wrapper ausente

**Erro típico:**
```
Could not find or load main class org.gradle.wrapper.GradleWrapperMain
```

**Solução - Baixar Gradle Wrapper:**

Abra PowerShell no diretório do projeto:
```powershell
cd C:\Users\mikan\Desktop\ReadModel\ReadCore\android

# Baixar Gradle
Invoke-WebRequest -Uri "https://services.gradle.org/distributions/gradle-8.0-bin.zip" -OutFile "gradle.zip"

# Extrair
Expand-Archive gradle.zip -DestinationPath .

# Copiar wrapper
Copy-Item "gradle-8.0\lib\gradle-wrapper.jar" "gradle\wrapper\"

# Limpar
Remove-Item gradle-8.0 -Recurse -Force
Remove-Item gradle.zip
```

**Ou usar Android Studio para gerar:**
```
Terminal no Android Studio:
gradle wrapper
```

---

### ✅ SOLUÇÃO B: SDK não instalado

**Erro típico:**
```
SDK location not found
```

**Solução:**
```
1. File → Project Structure (Ctrl+Alt+Shift+S)
2. SDK Location
3. Android SDK location: 
   C:\Users\mikan\AppData\Local\Android\Sdk
4. Apply → OK
```

**Se SDK não existe:**
```
Tools → SDK Manager
→ SDK Platforms: Marcar API 33 e API 34
→ SDK Tools: Marcar Build-Tools 34.0.0
→ Apply → Aguardar download
```

---

### ✅ SOLUÇÃO C: JAVA_HOME não configurado

**Erro típico:**
```
ERROR: JAVA_HOME is not set
```

**Solução:**
```
1. File → Project Structure
2. SDK Location
3. JDK location: Usar embedded JDK
   C:\Program Files\Android\Android Studio\jbr
4. Apply → OK
```

**Ou configurar manualmente:**

PowerShell como Administrador:
```powershell
[System.Environment]::SetEnvironmentVariable(
    'JAVA_HOME', 
    'C:\Program Files\Android\Android Studio\jbr', 
    'User'
)
```

Feche e reabra Android Studio.

---

### ✅ SOLUÇÃO D: Build falha com erro de compilação

**Ver erro completo:**
```
View → Tool Windows → Build
```

**Erros comuns:**

#### 1. Internet necessária:
```
Could not resolve all dependencies
```
→ Conecte à internet, Gradle precisa baixar dependências

#### 2. Versão do Gradle incompatível:
```
Edite: gradle/wrapper/gradle-wrapper.properties

Mude para:
distributionUrl=https\://services.gradle.org/distributions/gradle-8.0-bin.zip
```

#### 3. Compilação falhou:
```
Build → Clean Project
Build → Rebuild Project
```

---

## ❌ PROBLEMA 3: Build com SUCESSO mas app NÃO ABRE

### ✅ SOLUÇÃO A: Emulador não iniciou completamente

**Verificar:**
- Emulador deve mostrar tela inicial do Android
- Não pode estar em "Loading..."

**Solução:**
```
1. Feche o emulador
2. Device Manager → Cold Boot Now
3. Aguarde 2-3 minutos
4. Tente novamente
```

---

### ✅ SOLUÇÃO B: App instalou mas crashou

**Ver erro:**
```
View → Tool Windows → Logcat
Filtro: package:com.readcore.android
```

**Erros comuns:**

#### 1. Permissões:
```
Erro: Permission denied
```
→ No emulador/celular: Configurações → Apps → ReadCore → Permissões → Permitir

#### 2. API incompatível:
```
Erro: Requires API level 24
```
→ Emulador deve ser API 24 ou superior

#### 3. Biblioteca nativa ausente:
```
Erro: UnsatisfiedLinkError
```
→ Build → Clean Project → Rebuild

---

## 🔄 SOLUÇÃO UNIVERSAL (Quando tudo mais falhar)

### Método 1: Reset completo do projeto

```
1. Feche Android Studio
2. Delete as pastas:
   ReadCore\android\.gradle
   ReadCore\android\.idea
   ReadCore\android\build
   ReadCore\android\app\build

3. Reabra Android Studio
4. File → Sync Project with Gradle Files
5. Aguarde sync completo
6. Tente Run novamente
```

### Método 2: Reinstalar Android Studio

```
1. Desinstale Android Studio
2. Delete:
   C:\Users\mikan\.android
   C:\Users\mikan\.gradle
   C:\Users\mikan\AppData\Local\Android

3. Reinstale Android Studio
4. Execute Setup Wizard
5. Abra o projeto novamente
```

---

## 📋 CHECKLIST DE VERIFICAÇÃO

Marque cada item:

### Antes de tentar Run:

- [ ] Android Studio completamente carregado
- [ ] Barra inferior mostra: `Gradle sync finished` ✅
- [ ] Sem erros vermelhos no código
- [ ] Menu superior mostra: `[app ▼] [dispositivo ▼] [▶️]`
- [ ] Dispositivo conectado OU emulador rodando
- [ ] Terminal `adb devices` mostra dispositivo

### Se usar emulador:

- [ ] Emulador totalmente iniciado (vê tela inicial Android)
- [ ] Emulador não está em "Loading..."
- [ ] Emulador criado com API 24 ou superior

### Se usar celular:

- [ ] Depuração USB ativada
- [ ] Cabo USB funcionando (testar trocar)
- [ ] Autorização aceita no celular
- [ ] `adb devices` mostra "device" (não "unauthorized")

---

## 🎯 TESTE RÁPIDO

Execute este teste para verificar tudo:

```
1. View → Tool Windows → Terminal

2. Digite cada comando:

   # Verificar ADB
   adb version
   ✅ Deve mostrar versão

   # Verificar dispositivos
   adb devices
   ✅ Deve listar seu dispositivo

   # Verificar Gradle
   .\gradlew.bat tasks
   ✅ Deve listar tarefas disponíveis

   # Tentar build manual
   .\gradlew.bat assembleDebug
   ✅ Deve compilar sem erros

   # Instalar manual
   .\gradlew.bat installDebug
   ✅ Deve instalar no dispositivo
```

Se TODOS passarem, o problema é no Android Studio, não no projeto.

---

## 💡 DICAS EXTRAS

### Ver versão de tudo:

```
Terminal:
.\gradlew.bat --version

Deve mostrar:
------------------------------------------------------------
Gradle 8.0
------------------------------------------------------------

Build time:   2023-02-01
Revision:     abc123

Kotlin:       1.9.0
Groovy:       3.0.13
Ant:          Apache Ant(TM) 1.10.11
JVM:          17.0.7
OS:           Windows 11
```

### Forçar atualização de dependências:

```
.\gradlew.bat clean build --refresh-dependencies
```

### Compilar em modo verbose (ver tudo):

```
.\gradlew.bat assembleDebug --info
```

---

## 📞 AINDA NÃO FUNCIONA?

### Colete estas informações:

```
1. Screenshot do Android Studio (tela inteira)
2. Conteúdo de: View → Tool Windows → Build
3. Saída de: adb devices
4. Saída de: .\gradlew.bat --version
5. Arquivo: ReadCore\android\gradle\wrapper\gradle-wrapper.properties
```

### Logs úteis:

```
# Log do Gradle
ReadCore\android\.gradle\*.log

# Log do Android Studio
C:\Users\mikan\.AndroidStudio*\system\log\idea.log
```

---

## ✅ SOLUÇÃO MAIS COMUM (80% dos casos)

**O problema mais frequente é:**

1. **Gradle sync não completou**
   → Aguarde ou force: File → Sync Project with Gradle Files

2. **Nenhum dispositivo**
   → Crie emulador: Tools → Device Manager → Create Device

3. **Gradle wrapper ausente**
   → Use Android Studio para baixar automaticamente no primeiro sync

---

**🎯 Na maioria dos casos, basta:**
```
1. Aguardar Gradle sync terminar (barra inferior)
2. Criar/iniciar um emulador
3. Clicar Run ▶️
```

**⏱️ Primeira execução sempre demora mais!**
- Gradle sync: 5-15 minutos
- Build: 2-5 minutos
- Total: ~10-20 minutos na primeira vez
- Depois disso: apenas 1-3 minutos!

---

Qual erro específico você está vendo? Posso te ajudar com mais detalhes!
