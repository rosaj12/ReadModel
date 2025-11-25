# 🚀 COMO EXECUTAR - ReadCore Android

## ✨ GUIA RÁPIDO EM 3 PASSOS

### **PASSO 1: Abrir no Android Studio**

1. **Abra o Android Studio**
2. **File → Open**
3. **Navegue até:** `C:\Users\mikan\Desktop\ReadModel\ReadCore\android`
4. **Clique OK**
5. **AGUARDE** a sincronização do Gradle (5-15 min na primeira vez)
   - ⏳ Downloading dependencies...
   - ⏳ Syncing...
   - ✅ Gradle sync finished (aparecerá na barra inferior)

> **💡 IMPORTANTE:** O Gradle vai baixar automaticamente:
> - Gradle Wrapper (~7 MB)
> - Dependências do projeto (~500 MB)
> - Build tools do Android
> 
> **Isso só acontece na primeira vez!**

---

### **PASSO 2: Configurar Dispositivo**

Escolha uma opção:

#### **Opção A: Emulador (Simulador)**

1. No Android Studio: **Tools → Device Manager**
2. Clique **Create Device**
3. Selecione um modelo: **Pixel 6** (recomendado)
4. **Next**
5. Baixe uma imagem do sistema: **API 33 (Tiramisu)** ou **API 34**
   - Clique em **Download** ao lado da API
   - Aguarde download (~1 GB)
6. **Next** → **Finish**
7. Clique no **▶️** (play) para iniciar o emulador
8. Aguarde o emulador carregar (~2 minutos)

#### **Opção B: Celular Real (MAIS RÁPIDO)**

1. **No celular Android:**
   ```
   Configurações → Sobre o telefone
   → Toque 7 vezes em "Número da versão"
   → Volta → Opções do desenvolvedor
   → Ative "Depuração USB"
   ```

2. **Conecte o celular ao PC via cabo USB**

3. **No celular:** Aceite a permissão "Permitir depuração USB?"

4. **Verifique conexão:**
   - No Android Studio, você verá seu celular listado no topo
   - Ou execute: `adb devices` (deve mostrar seu dispositivo)

---

### **PASSO 3: Executar o App**

1. **No Android Studio:**
   - Clique no botão verde **▶️ Run**
   - Ou pressione: **Shift + F10**

2. **Selecione o dispositivo:**
   - Escolha seu emulador ou celular

3. **Aguarde:**
   - ⏳ Building... (1-3 minutos primeira vez)
   - ⏳ Installing APK...
   - ✅ Launching activity...

4. **O app abrirá automaticamente!** 🎉

---

## 🎯 USANDO OS SCRIPTS AUTOMATIZADOS

Criei 2 scripts para facilitar:

### **1️⃣ compilar.bat** - Compilar o APK

```batch
# No Windows Explorer:
1. Navegue até: C:\Users\mikan\Desktop\ReadModel\ReadCore\android
2. Duplo-clique em: compilar.bat
3. Aguarde a compilação
4. APK gerado em: app\build\outputs\apk\debug\app-debug.apk
```

### **2️⃣ instalar.bat** - Instalar no Dispositivo

```batch
# No Windows Explorer:
1. Conecte o celular ou inicie o emulador
2. Duplo-clique em: instalar.bat
3. O app será instalado automaticamente
```

**Ou via PowerShell:**
```powershell
cd C:\Users\mikan\Desktop\ReadModel\ReadCore\android

# Compilar
.\compilar.bat

# Instalar
.\instalar.bat
```

---

## 🛠️ COMANDOS MANUAIS (PowerShell)

Se preferir comandos diretos:

```powershell
# Navegar até o projeto
cd C:\Users\mikan\Desktop\ReadModel\ReadCore\android

# Compilar APK Debug
.\gradlew.bat assembleDebug

# Instalar no dispositivo
.\gradlew.bat installDebug

# Limpar e recompilar
.\gradlew.bat clean assembleDebug

# Ver dispositivos conectados
adb devices

# Desinstalar app
adb uninstall com.readcore.android

# Ver logs do app
adb logcat | Select-String "ReadCore"
```

---

## 📱 TESTANDO O APP

Após instalado:

### **1. Adicionar Livros**

O app precisa de permissões para acessar arquivos:

1. **Toque no botão + (adicionar livro)**
2. **Permita acesso aos arquivos** quando solicitado
3. **Navegue até seus PDFs/EPUBs**
4. **Selecione um arquivo**
5. **O livro aparecerá na biblioteca!**

### **2. Adicionar Arquivos de Teste**

**No Emulador:**
```powershell
# Enviar arquivo PDF para o emulador
adb push "C:\caminho\para\livro.pdf" /sdcard/Download/

# Verificar
adb shell ls /sdcard/Download/
```

Depois, no app:
- Toque em +
- Navegue até Download
- Selecione o arquivo

**No Celular Real:**
- Baixe PDFs da internet
- Ou copie via USB para a pasta Download
- Ou use Google Drive/Dropbox

### **3. Testar Funcionalidades**

✅ **Biblioteca:**
- Adicionar livros (PDF, EPUB, TXT)
- Buscar livros (toque na lupa)
- Remover livros (toque e segure → confirme)

✅ **Leitura:**
- Abrir livro (toque no livro)
- Navegar páginas (setas < >)
- Ajustar fonte (A- / A+)
- Criar marcador (ícone marcador)
- Ver marcadores (ícone lista)
- Ir para página (ícone páginas)

---

## ❓ SOLUÇÃO DE PROBLEMAS

### **Gradle sync falhou**

**Solução 1:** Reabrir projeto
```
File → Invalidate Caches → Invalidate and Restart
```

**Solução 2:** Limpar cache
```powershell
.\gradlew.bat clean --refresh-dependencies
```

**Solução 3:** Deletar .gradle
```powershell
Remove-Item -Recurse -Force .gradle
# Reabrir Android Studio
```

---

### **Dispositivo não detectado**

**Emulador:**
```
1. Device Manager → Start emulador
2. Aguarde carregar completamente
3. Execute novamente
```

**Celular:**
```powershell
# Reiniciar ADB
adb kill-server
adb start-server
adb devices

# Deve mostrar seu dispositivo
```

Se não aparecer:
- Verifique cabo USB (teste outro cabo)
- Desative e reative "Depuração USB"
- Teste outra porta USB
- Instale drivers USB do fabricante

---

### **APK não instala**

```powershell
# Ver erro detalhado
.\gradlew.bat installDebug --info

# Desinstalar versão antiga
adb uninstall com.readcore.android

# Tentar novamente
.\gradlew.bat installDebug
```

---

### **App crasha ao abrir**

```powershell
# Ver logs em tempo real
adb logcat | Select-String "AndroidRuntime"

# Ver logs do ReadCore
adb logcat | Select-String "ReadCore"

# Salvar logs em arquivo
adb logcat > logs.txt
```

---

### **Erro de permissões**

No app:
```
1. Configurações do Android → Apps → ReadCore
2. Permissões → Arquivos
3. Permitir acesso
```

---

## 📊 VERIFICAR STATUS DA COMPILAÇÃO

```powershell
# Ver versão do Gradle
.\gradlew.bat --version

# Listar todas as tasks
.\gradlew.bat tasks

# Ver dependências
.\gradlew.bat dependencies

# Verificar lint (erros de código)
.\gradlew.bat lint
```

---

## 🎯 RESUMO DO PROCESSO

```
1. Android Studio → Open → android/
2. Aguardar Gradle sync (automático)
3. Tools → Device Manager → Create/Start device
4. Run ▶️ → Select device
5. App instalado e aberto! ✅
```

**Primeira compilação:** ~15-20 minutos (downloads)  
**Compilações seguintes:** ~1-3 minutos (cache)

---

## 📚 RECURSOS ADICIONAIS

- **Documentação completa:** Veja `COMO-COMPILAR.md`
- **Informações do APK:** Veja `APK-INFO.md`
- **Arquitetura:** Veja `ARQUITETURA.md`

---

## ✅ CHECKLIST

Antes de executar, verifique:

- [ ] Android Studio instalado
- [ ] Projeto sincronizado (Gradle sync completo)
- [ ] Dispositivo conectado OU emulador iniciado
- [ ] Depuração USB ativada (celular real)
- [ ] Internet disponível (primeira compilação)

---

**🎉 Pronto! Agora você pode compilar e executar o ReadCore Android!**

Qualquer dúvida, consulte os arquivos `.md` na pasta ou veja os logs de erro.
