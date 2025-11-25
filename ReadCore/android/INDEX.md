# 📱 ReadCore - Aplicação Mobile Android

**Leitor de Livros Digitais para Android com Clean Architecture**

Aplicação Android nativa desenvolvida em Kotlin com Jetpack Compose que permite ler livros nos formatos PDF, EPUB, TXT e MOBI. Implementa Clean Architecture para máxima testabilidade e manutenibilidade.

---

## ✨ Características Principais

### 📚 Funcionalidades
- ✅ Suporte a múltiplos formatos: **PDF, EPUB, TXT, MOBI**
- ✅ Biblioteca de livros com **busca por título**
- ✅ Leitor com **navegação de páginas** (anterior/próxima/ir para)
- ✅ **Marcadores** com anotações opcionais
- ✅ **Progresso de leitura** salvo automaticamente
- ✅ **Ajuste de tamanho de fonte** em tempo real
- ✅ **Armazenamento offline** completo (DataStore)
- ✅ Interface em **Material Design 3**
- ✅ Tema claro/escuro automático

### 🏗️ Tecnologias
- **Linguagem**: Kotlin
- **UI**: Jetpack Compose + Material Design 3
- **Arquitetura**: Clean Architecture (4 camadas)
- **Persistência**: DataStore Preferences
- **PDF**: PDFBox Android 2.0.27.0
- **EPUB**: JSoup 1.16.1
- **JSON**: Gson 2.10.1
- **Async**: Kotlin Coroutines
- **Navigation**: Navigation Compose
- **Build**: Gradle 8.0 + Kotlin DSL

---

## 🚀 Início Rápido

### Pré-requisitos
- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17+
- Android SDK (API 24-34)
- Dispositivo Android ou emulador (API 24+)

### Compilar e Executar

#### No Android Studio:
1. Abra a pasta `ReadCore/android` no Android Studio
2. Aguarde sincronização do Gradle
3. Clique em **Run** (▶️) ou pressione `Shift+F10`

#### Via linha de comando (Windows):
```powershell
cd ReadCore\android
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
```

#### Via linha de comando (Linux/Mac):
```bash
cd ReadCore/android
./gradlew assembleDebug
./gradlew installDebug
```

O APK será gerado em: `app/build/outputs/apk/debug/app-debug.apk`

---

## 📖 Documentação Completa

### 📚 Guias Disponíveis

| Documento | Descrição |
|-----------|-----------|
| **[README.md](README.md)** | Visão geral e características |
| **[QUICKSTART.md](QUICKSTART.md)** | Guia rápido de compilação e teste |
| **[COMO-COMPILAR.md](COMO-COMPILAR.md)** | Tutorial completo passo a passo |
| **[APK-INFO.md](APK-INFO.md)** | Informações sobre build e distribuição do APK |
| **[ARQUITETURA.md](ARQUITETURA.md)** | Detalhes da Clean Architecture implementada |

### 🎯 Escolha seu guia:

- **Nunca compilou Android?** → Comece com [COMO-COMPILAR.md](COMO-COMPILAR.md)
- **Quer compilar rápido?** → Veja [QUICKSTART.md](QUICKSTART.md)
- **Quer entender o código?** → Leia [ARQUITETURA.md](ARQUITETURA.md)
- **Vai distribuir o app?** → Consulte [APK-INFO.md](APK-INFO.md)

---

## 🏛️ Arquitetura

ReadCore segue **Clean Architecture** com 4 camadas bem definidas:

```
┌─────────────────────────────────────────┐
│  Framework (UI, ViewModels, Android)    │  ← Jetpack Compose
├─────────────────────────────────────────┤
│  Adapters (Repos, Readers, DataStore)  │  ← PDFBox, JSoup
├─────────────────────────────────────────┤
│  Use Cases (Business Logic)            │  ← Pure Kotlin
├─────────────────────────────────────────┤
│  Domain (Entities, Interfaces)         │  ← Zero dependencies
└─────────────────────────────────────────┘
```

### Estrutura do Código:

```
app/src/main/java/com/readcore/android/
├── domain/          # Entidades e contratos
│   ├── entities/    # Book, Bookmark, ReadingProgress
│   └── repositories/# Interfaces
├── usecases/        # Regras de negócio
├── adapters/        # Implementações
│   ├── repositories/# JSON com DataStore
│   └── readers/     # PDF, EPUB, TXT readers
└── framework/       # Android-specific
    ├── ui/          # Compose screens
    ├── viewmodels/  # State management
    └── AppContainer.kt  # DI manual
```

**Benefícios:**
- ✅ Testável sem emulador
- ✅ Independente de frameworks
- ✅ Fácil manutenção
- ✅ Escalável e extensível

[Veja detalhes completos em ARQUITETURA.md](ARQUITETURA.md)

---

## 📱 Screenshots e Funcionalidades

### Tela Principal - Biblioteca
- Lista todos os livros adicionados
- Busca por título
- Informações: título, autor, formato, tamanho
- Botão flutuante (+) para adicionar livros
- Long press para remover livros

### Tela de Leitura
- Exibição do conteúdo do livro
- Navegação: anterior, próxima, ir para página
- Barra de ferramentas:
  - Ajustar fonte (A- / A+)
  - Criar marcador
  - Ver marcadores
  - Ir para página específica
- Progresso automático salvo

### Funcionalidades Extras
- Permissões gerenciadas automaticamente
- Suporte a Android 7.0+ (API 24+)
- Compatível com Storage Access Framework
- Persistência offline total

---

## 🔧 Requisitos Técnicos

### Sistema Operacional
- **Mínimo**: Android 7.0 (API 24) - Nougat
- **Alvo**: Android 14 (API 34)
- **Recomendado**: Android 11+ (API 30+)

### Permissões
- `READ_EXTERNAL_STORAGE` (Android ≤ 12)
- `READ_MEDIA_DOCUMENTS` (Android ≥ 13)

### Tamanho
- **APK Debug**: ~15-20 MB
- **APK Release**: ~10-15 MB (com ProGuard)
- **Instalação**: ~25-30 MB

### Hardware
- **RAM**: Mínimo 2 GB (4 GB recomendado)
- **Armazenamento**: 50 MB + espaço para livros
- **Processador**: Qualquer ARM ou x86

---

## 🧪 Como Testar

### Adicionar Livros de Teste

1. **Via ADB** (mais rápido):
```powershell
adb push livro.pdf /sdcard/Download/
```

2. **No dispositivo**:
- Baixe PDFs/EPUBs da internet
- Use Google Drive ou outros apps
- Copie via cabo USB para `Download/`

3. **No emulador**:
- Arraste arquivos para a janela do emulador
- Use o Device File Explorer do Android Studio

### Testar Funcionalidades

✅ **Adicionar livro**: Toque em + → Selecione arquivo  
✅ **Abrir livro**: Toque no livro da lista  
✅ **Navegar páginas**: Use setas ou swipe  
✅ **Criar marcador**: Toque no ícone de marcador  
✅ **Buscar**: Toque na lupa → Digite título  
✅ **Ajustar fonte**: Use A- e A+ na leitura  
✅ **Remover livro**: Long press no livro → Confirme  

---

## 🛠️ Desenvolvimento

### Estrutura do Projeto

```
android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/          # Código Kotlin
│   │   │   ├── res/           # Recursos (strings, temas)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/              # Testes unitários
│   │   └── androidTest/       # Testes instrumentados
│   └── build.gradle.kts       # Config do módulo
├── gradle/                     # Gradle wrapper
├── build.gradle.kts           # Config raiz
├── settings.gradle.kts        # Settings
└── *.md                       # Documentação
```

### Comandos Úteis

```powershell
# Limpar build
.\gradlew.bat clean

# Compilar debug
.\gradlew.bat assembleDebug

# Compilar release
.\gradlew.bat assembleRelease

# Instalar
.\gradlew.bat installDebug

# Rodar testes
.\gradlew.bat test

# Lint
.\gradlew.bat lint

# Ver dependências
.\gradlew.bat dependencies
```

### Adicionar Funcionalidades

1. **Nova entidade** → `domain/entities/`
2. **Novo use case** → `usecases/`
3. **Nova tela** → `framework/ui/screens/`
4. **Novo ViewModel** → `framework/viewmodels/`

Mantenha a Clean Architecture: dependências sempre apontam para dentro!

---

## 🐛 Solução de Problemas

### Build falha com "SDK not found"
Crie `local.properties`:
```properties
sdk.dir=C\:\\Users\\<USUARIO>\\AppData\\Local\\Android\\Sdk
```

### Erro de permissões ao abrir livros
1. Vá em Configurações → Apps → ReadCore
2. Permissões → Arquivos
3. Permita acesso

### APK não instala
- Habilite "Fontes desconhecidas" em Segurança
- Desinstale versão anterior primeiro
- Verifique espaço disponível

### Gradle muito lento
Edite `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m
org.gradle.parallel=true
org.gradle.caching=true
```

[Mais soluções em COMO-COMPILAR.md](COMO-COMPILAR.md)

---

## 📦 Distribuição

### Google Play Store
1. Compile App Bundle: `.\gradlew.bat bundleRelease`
2. Gera: `app/build/outputs/bundle/release/app-release.aab`
3. Faça upload no Google Play Console

### Distribuição Direta
1. Compile APK Release assinado
2. Compartilhe o arquivo APK
3. Usuários instalam manualmente

### F-Droid
1. Publique código-fonte
2. Submeta à F-Droid
3. Build automático pelos servidores

[Detalhes em APK-INFO.md](APK-INFO.md)

---

## 🔮 Roadmap Futuro

### Funcionalidades Planejadas
- [ ] Modo noturno personalizado (sépia, preto)
- [ ] Compartilhar citações/trechos
- [ ] Anotações com destaque de texto
- [ ] Sincronização na nuvem (Firebase/Drive)
- [ ] Estatísticas de leitura
- [ ] Categorias e tags
- [ ] Leitor de audiobook (TTS)
- [ ] Temas personalizáveis

### Melhorias Técnicas
- [ ] Testes unitários completos
- [ ] Testes instrumentados (UI)
- [ ] CI/CD com GitHub Actions
- [ ] Room Database (substituir DataStore)
- [ ] WorkManager para sync
- [ ] Kotlin Multiplatform (iOS)

---

## 🤝 Contribuindo

### Como Contribuir
1. Fork o repositório
2. Crie uma branch: `git checkout -b feature/nova-funcionalidade`
3. Commit: `git commit -m 'Adiciona nova funcionalidade'`
4. Push: `git push origin feature/nova-funcionalidade`
5. Abra um Pull Request

### Padrões de Código
- Siga Kotlin coding conventions
- Mantenha Clean Architecture
- Adicione testes para novas features
- Documente mudanças no código

---

## 📄 Licença

Este projeto foi desenvolvido como exemplo educacional de Clean Architecture em Android.

---

## 📚 Recursos e Referências

### Documentação Oficial
- [Android Developers](https://developer.android.com)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Bibliotecas Usadas
- [PDFBox Android](https://github.com/TomRoush/PdfBox-Android)
- [JSoup](https://jsoup.org/)
- [Gson](https://github.com/google/gson)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)

### Tutoriais
- [Gradle Build Tool](https://docs.gradle.org/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Material Design 3](https://m3.material.io/)

---

## 👨‍💻 Autor

Desenvolvido seguindo as melhores práticas de:
- ✅ Clean Architecture
- ✅ SOLID Principles
- ✅ Dependency Injection
- ✅ Repository Pattern
- ✅ MVVM Architecture

---

## 💬 Suporte

Para dúvidas ou problemas:
1. Consulte a documentação (.md files)
2. Verifique issues existentes
3. Crie uma nova issue descrevendo o problema

---

## 🎯 Status do Projeto

**Versão**: 1.0.0  
**Status**: ✅ Funcional e completo  
**Última atualização**: Novembro 2025

### Funcionalidades Implementadas: 100%
- ✅ Biblioteca de livros
- ✅ Leitura multi-formato
- ✅ Marcadores
- ✅ Progresso automático
- ✅ Busca
- ✅ Ajuste de fonte
- ✅ Material Design 3
- ✅ Clean Architecture

---

**Desenvolvido com ❤️ usando Kotlin e Jetpack Compose**
