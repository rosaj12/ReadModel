# 🚀 Como Executar no Android Studio

## Passo a Passo Rápido

### 1️⃣ Abrir o Projeto

1. Abra o **Android Studio**
2. Clique em **File → Open**
3. Navegue até: `C:\Users\mikan\Desktop\ReadModel\ReadCore\android`
4. Clique em **OK**

### 2️⃣ Aguardar Gradle Sync

Quando abrir o projeto, o Android Studio vai automaticamente:

- 📥 Baixar Gradle 8.0 (~100 MB)
- 📥 Baixar dependências (PDFBox, Compose, etc) (~500 MB)
- ⚙️ Compilar o projeto

**Aguarde a mensagem:** `Gradle sync finished in Xm Ys` ✅

⏱️ **Tempo:** 5-15 minutos na primeira vez

### 3️⃣ Configurar Emulador

1. Clique em **Tools → Device Manager**
2. Clique em **Create Device**
3. Selecione um dispositivo (ex: **Pixel 5**)
4. Escolha uma imagem do sistema (ex: **API 34 - Android 14**)
5. Clique em **Finish**

### 4️⃣ Executar o Aplicativo

1. Selecione o emulador criado na barra superior
2. Clique no botão **Run ▶️** (ou pressione `Shift + F10`)
3. O emulador vai abrir e o app será instalado automaticamente

## 🎉 Pronto!

O aplicativo **ReadCore** vai abrir no emulador!

---

## ⚠️ Problemas Comuns

### ❌ Botão Run desabilitado?
- Aguarde o Gradle sync terminar completamente
- Verifique se não há erros na aba **Build**

### ❌ Erro "No devices configured"?
- Configure um emulador seguindo o passo 3

### ❌ Erro "SDK not found"?
- File → Settings → Appearance & Behavior → System Settings → Android SDK
- Certifique-se que Android 14.0 (API 34) está instalado

### ❌ Gradle sync falhou?
- Verifique sua conexão com a internet
- File → Invalidate Caches → Invalidate and Restart

---

## 📱 Executar em Dispositivo Físico

1. Ative **Opções do desenvolvedor** no seu Android:
   - Configurações → Sobre o telefone
   - Toque 7 vezes em "Número da versão"

2. Ative **Depuração USB**:
   - Configurações → Opções do desenvolvedor
   - Ative "Depuração USB"

3. Conecte o celular no computador via USB

4. No Android Studio:
   - Selecione seu dispositivo na barra superior
   - Clique em **Run ▶️**

---

## 📚 Mais Documentação

- **ANDROID-STUDIO.md** - Configuração completa e detalhada
- **GUIA-VISUAL.md** - Guia visual com imagens
- **TROUBLESHOOTING-RUN.md** - Soluções para todos os problemas
- **EXECUTAR.md** - 3 métodos diferentes de compilação

---

## 🎯 Resumo Ultra Rápido

```
1. File → Open → android/
2. Aguarde "Gradle sync finished" ✅
3. Tools → Device Manager → Create Device
4. Run ▶️ (Shift+F10)
5. Pronto! 🎉
```
