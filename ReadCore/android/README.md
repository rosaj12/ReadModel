# ReadCore Android

Aplicativo Android de leitura de livros digitais desenvolvido com Kotlin e Jetpack Compose, seguindo os princípios de Clean Architecture.

## 📱 Características

- **Suporte a múltiplos formatos**: PDF, EPUB, TXT, MOBI
- **Interface moderna**: Desenvolvida com Jetpack Compose e Material Design 3
- **Clean Architecture**: Separação clara entre camadas (Domain, Use Cases, Adapters, Framework)
- **Armazenamento offline**: Persistência de dados com DataStore
- **Funcionalidades de leitura**:
  - Navegação por páginas
  - Marcadores com anotações
  - Progresso de leitura salvo automaticamente
  - Ajuste de tamanho de fonte
  - Busca de livros por título

## 🏗️ Arquitetura

```
android/
├── app/
│   └── src/main/java/com/readcore/android/
│       ├── domain/              # Entidades e interfaces (sem dependências)
│       │   ├── entities/
│       │   └── repositories/
│       ├── usecases/            # Regras de negócio da aplicação
│       ├── adapters/            # Implementações de I/O
│       │   ├── repositories/    # Repositórios JSON com DataStore
│       │   └── readers/         # Leitores de diferentes formatos
│       └── framework/           # UI e infraestrutura
│           ├── ui/
│           │   ├── screens/
│           │   └── theme/
│           └── viewmodels/
```

### Camadas:

1. **Domain**: Entidades e contratos de repositórios (sem dependências Android)
2. **Use Cases**: Lógica de negócio pura
3. **Adapters**: Implementação de leitores (PDF, EPUB, TXT) e persistência (DataStore)
4. **Framework**: UI com Jetpack Compose, ViewModels, navegação

## 🚀 Tecnologias

- **Kotlin**: Linguagem principal
- **Jetpack Compose**: UI moderna e declarativa
- **Material Design 3**: Design system
- **Navigation Compose**: Navegação entre telas
- **DataStore**: Persistência de dados
- **PDFBox Android**: Leitura de arquivos PDF
- **JSoup**: Parsing de EPUB (HTML/XML)
- **Gson**: Serialização JSON
- **Coroutines**: Programação assíncrona

## 📋 Requisitos

- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17
- Android SDK API 34
- Gradle 8.0+

## 📚 Guias de Configuração e Compilação

Escolha o guia adequado ao seu nível:

| Guia | Descrição | Recomendado para |
|------|-----------|------------------|
| **[ANDROID-STUDIO.md](ANDROID-STUDIO.md)** ⭐ | Guia completo de configuração do Android Studio | Primeira vez usando Android Studio |
| **[GUIA-VISUAL.md](GUIA-VISUAL.md)** | Versão simplificada com diagramas visuais | Quem prefere guias visuais |
| **[EXECUTAR.md](EXECUTAR.md)** | Como compilar e executar (3 métodos) | Já tem Android Studio instalado |
| **[COMO-COMPILAR.md](COMO-COMPILAR.md)** | Tutorial detalhado passo a passo | Troubleshooting e detalhes |
| **[QUICKSTART.md](QUICKSTART.md)** | Referência rápida de comandos | Desenvolvedores experientes |
| **[APK-INFO.md](APK-INFO.md)** | Informações sobre build e distribuição | Publicar o app |

### 🎯 Início Rápido

**Nunca usou Android Studio?**
1. Leia: [ANDROID-STUDIO.md](ANDROID-STUDIO.md)
2. Siga os 4 passos: Instalar → Abrir → Configurar → Executar

**Já tem Android Studio?**
1. File → Open → Selecione pasta `android/`
2. Aguarde Gradle sync
3. Run ▶️ (Shift+F10)

## 🔧 Como compilar

1. Clone o repositório e navegue até o diretório android:
```bash
cd ReadCore/android
```

2. Abra o projeto no Android Studio

3. Sincronize o projeto com os arquivos Gradle

4. Execute o build:
```bash
./gradlew assembleDebug
```

## 📦 Gerando o APK

### APK de Debug:
```bash
./gradlew assembleDebug
```
APK gerado em: `app/build/outputs/apk/debug/app-debug.apk`

### APK de Release (assinado):
```bash
./gradlew assembleRelease
```
APK gerado em: `app/build/outputs/apk/release/app-release.apk`

## 🎯 Como usar

1. Instale o APK no dispositivo Android
2. Conceda permissões de leitura de arquivos
3. Toque no botão + para adicionar livros
4. Selecione um arquivo PDF, EPUB ou TXT
5. Toque no livro para começar a leitura

### Funcionalidades durante a leitura:

- **Navegação**: Use os botões de seta para avançar/voltar páginas
- **Marcadores**: Toque no ícone de marcador para salvar a página atual
- **Ir para página**: Toque no ícone de páginas para pular para uma página específica
- **Ajustar fonte**: Use os botões A- e A+ para diminuir/aumentar o texto
- **Visualizar marcadores**: Acesse seus marcadores e navegue diretamente para eles

## 📱 Permissões necessárias

- `READ_EXTERNAL_STORAGE` (Android 12 e inferior)
- `READ_MEDIA_DOCUMENTS` (Android 13+)

## 🧪 Testando no emulador

1. Crie um emulador Android no Android Studio (API 24+)
2. Execute:
```bash
./gradlew installDebug
```
3. Adicione arquivos de teste ao emulador usando o Android File Explorer

## 📝 Estrutura de dados

Os dados são armazenados usando DataStore em três arquivos:
- `books.preferences_pb`: Biblioteca de livros
- `reading_progress.preferences_pb`: Progresso de leitura
- `bookmarks.preferences_pb`: Marcadores salvos

## 🔄 Integração com versão Desktop

Este aplicativo compartilha a mesma arquitetura Clean Architecture da versão desktop (Java/Swing), permitindo:
- Reutilização da lógica de negócio
- Mesmas entidades de domínio
- Contratos de repositórios idênticos

## 🐛 Resolução de problemas

### Erro de permissão ao abrir arquivos:
- Certifique-se de conceder permissões de armazenamento nas configurações do app

### APK não instala:
- Habilite "Fontes desconhecidas" nas configurações de segurança

### Erro ao abrir PDF:
- Verifique se o arquivo não está corrompido
- PDFs protegidos por senha não são suportados

## 📄 Licença

Este projeto foi desenvolvido como exemplo educacional de Clean Architecture em Android.

## 👨‍💻 Desenvolvimento

Desenvolvido seguindo as melhores práticas:
- SOLID principles
- Clean Architecture
- Dependency Injection manual
- Separation of Concerns
- Single Responsibility
- Repository Pattern
