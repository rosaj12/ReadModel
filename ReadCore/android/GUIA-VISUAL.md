# 🎯 GUIA VISUAL RÁPIDO - Android Studio

## 4 PASSOS SIMPLES

---

## 1️⃣ INSTALAR ANDROID STUDIO

```
┌─────────────────────────────────────────┐
│  https://developer.android.com/studio  │
│                                         │
│  [Download Android Studio]  ◄── CLIQUE │
└─────────────────────────────────────────┘

Instalação:
Next → Next → Next → Install → Aguarde → Finish

Setup Wizard:
Next → Standard → Next → Accept → Finish
Aguarde downloads (~10-30 min)
```

---

## 2️⃣ ABRIR PROJETO

```
Android Studio - Tela Inicial
┌─────────────────────────────────┐
│                                 │
│   New Project                   │
│   Open                      ◄───── CLIQUE
│   Get from VCS                  │
│                                 │
└─────────────────────────────────┘

Navegue até:
C:\Users\mikan\Desktop\ReadModel\ReadCore\android
                                            ^^^^^^
Selecione → OK

Aguarde barra inferior:
[████████████████] Gradle sync finished ✅
```

---

## 3️⃣ CONFIGURAR DISPOSITIVO

### OPÇÃO A: EMULADOR

```
Tools → Device Manager
┌─────────────────────────────────────┐
│  Device Manager                     │
├─────────────────────────────────────┤
│                                     │
│  [+ Create Device]  ◄────────── CLIQUE
│                                     │
└─────────────────────────────────────┘

1. Select Hardware:
   Phone → Pixel 6 → Next

2. System Image:
   Tiramisu (API 33) → Download → Next
   (Aguarde ~1 GB)

3. Verify:
   AVD Name: Pixel_6_API_33
   Finish

4. Iniciar:
   [▶️]  ◄────────────────────────── CLIQUE
   Aguarde ~2 min
```

### OPÇÃO B: CELULAR

```
NO CELULAR:
1. Configurações → Sobre o telefone
2. Toque 7x em "Número da versão"
3. Volte → Opções do desenvolvedor
4. Ative "Depuração USB"

NO PC:
1. Conecte celular via USB
2. No celular: "Permitir depuração USB?" → OK
3. Android Studio mostrará seu celular:
   
   ┌──────────────────────────────┐
   │  Samsung Galaxy A52          │  ◄── Aparecerá aqui
   └──────────────────────────────┘
```

---

## 4️⃣ EXECUTAR

```
Barra superior do Android Studio:
┌────────────────────────────────────────┐
│  [app ▼] [Pixel_6 ▼] [▶️ Run]         │
│                        ^^^^^^          │
│                     CLIQUE AQUI        │
└────────────────────────────────────────┘

Ou pressione: Shift + F10

Console inferior mostrará:
┌────────────────────────────────────────┐
│  > Task :app:assembleDebug             │
│  BUILD SUCCESSFUL in 2m 15s            │
│  Installing APK...                     │
│  Launching activity...                 │
│  ✅ App executado com sucesso!         │
└────────────────────────────────────────┘

APP ABRIRÁ NO DISPOSITIVO! 🎉
```

---

## 📱 TELA INICIAL DO APP

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
│                          [+]  ◄──│ TOQUE AQUI
└──────────────────────────────────┘
```

---

## 🧪 TESTAR APP

### Adicionar Arquivo de Teste:

**No emulador:**
```
Android Studio → Terminal (Alt+F12)

Digite:
adb push "C:\caminho\para\livro.pdf" /sdcard/Download/

No app:
Toque [+] → Download → Selecione arquivo
```

**No celular:**
```
1. Baixe um PDF qualquer no celular
2. No app: Toque [+]
3. Navegue até Download
4. Selecione o PDF
5. Pronto! Livro adicionado ✅
```

### Testar Leitura:

```
1. Toque no livro da lista
2. App abre tela de leitura
3. Use setas: [<] [>]
4. Ajustar fonte: [A-] [A+]
5. Criar marcador: [🔖]
```

---

## 🔧 FERRAMENTAS ÚTEIS

### Ver Logs (Logcat):
```
View → Tool Windows → Logcat

Ou clique na aba inferior:
[Logcat] ◄── CLIQUE

Filtrar por ReadCore:
package:com.readcore.android
```

### Enviar Arquivos (Device File Explorer):
```
View → Tool Windows → Device File Explorer

Navegue:
📁 sdcard → 📁 Download

Arraste PDFs para cá →
```

### Terminal:
```
View → Tool Windows → Terminal

Ou: Alt + F12

Comandos úteis:
adb devices          ← Ver dispositivos
adb logcat           ← Ver logs
adb push file.pdf    ← Enviar arquivo
```

---

## ❌ PROBLEMAS COMUNS

### Gradle sync failed:
```
File → Invalidate Caches → Invalidate and Restart
```

### Dispositivo não detectado:
```
Emulador: Reiniciar emulador
Celular: adb kill-server → adb start-server
```

### Build failed:
```
1. Verificar internet
2. Tools → SDK Manager → Instalar API 33
3. Build → Clean Project → Rebuild
```

---

## 📊 STATUS DE SUCESSO

Você está pronto quando:

✅ Android Studio aberto  
✅ Projeto sincronizado (sem erros vermelhos)  
✅ Dispositivo conectado/emulador rodando  
✅ App executado (botão Run ▶️)  
✅ App abriu no dispositivo  
✅ Consegue adicionar livro  
✅ Consegue ler livro  

---

## 📚 DOCUMENTAÇÃO COMPLETA

Para guia detalhado com screenshots e troubleshooting:

```
📄 ANDROID-STUDIO.md  ← Guia completo (LEIA ESTE!)
```

---

## ⏱️ TEMPO TOTAL

Primeira vez:
- Instalar Android Studio: ~15 min
- Setup Wizard: ~20 min
- Abrir projeto + Gradle sync: ~10 min
- Criar emulador: ~5 min
- Executar app: ~3 min

**TOTAL: ~50-60 minutos**

Depois disso, compilações levam apenas 1-3 minutos! ⚡

---

**✅ PRONTO! Agora é só desenvolver!**

Dica: Pressione **Shift** duas vezes para busca rápida de arquivos!
