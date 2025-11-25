# ReadCore - Arquitetura e Estrutura

## Visão Geral da Arquitetura Clean

```
┌─────────────────────────────────────────────────────────────┐
│                        Framework Layer                       │
│  ┌─────────────────────────────────────────────────────┐   │
│  │              UI (Swing Components)                   │   │
│  │  • MainWindow - Gerenciamento de biblioteca         │   │
│  │  • ReaderWindow - Interface de leitura              │   │
│  │  • Main - Bootstrap e DI                            │   │
│  └─────────────────────────────────────────────────────┘   │
└───────────────────────┬─────────────────────────────────────┘
                        │ depende de
┌───────────────────────▼─────────────────────────────────────┐
│                      Adapters Layer                          │
│  ┌──────────────────────┐  ┌──────────────────────────┐    │
│  │   Persistence        │  │   Book Readers           │    │
│  │  • JsonBookRepo      │  │  • PdfReader             │    │
│  │  • JsonProgressRepo  │  │  • EpubBookReader        │    │
│  │  • JsonBookmarkRepo  │  │  • TxtReader             │    │
│  │                      │  │  • MultiFormatReader     │    │
│  └──────────────────────┘  └──────────────────────────┘    │
└───────────────────────┬─────────────────────────────────────┘
                        │ implementa interfaces
┌───────────────────────▼─────────────────────────────────────┐
│                     Use Cases Layer                          │
│  • AddBookUseCase - Adicionar livro                         │
│  • GetAllBooksUseCase - Listar livros                       │
│  • OpenBookUseCase - Abrir livro                            │
│  • ReadPageUseCase - Ler página                             │
│  • UpdateReadingProgressUseCase - Atualizar progresso       │
│  • GetReadingProgressUseCase - Obter progresso              │
│  • CreateBookmarkUseCase - Criar marcador                   │
│  • GetBookmarksUseCase - Obter marcadores                   │
│  • RemoveBookUseCase - Remover livro                        │
│  • SearchBooksUseCase - Buscar livros                       │
└───────────────────────┬─────────────────────────────────────┘
                        │ usa apenas
┌───────────────────────▼─────────────────────────────────────┐
│                       Domain Layer                           │
│  ┌──────────────────────┐  ┌──────────────────────────┐    │
│  │     Entities         │  │  Repository Interfaces    │    │
│  │  • Book              │  │  • BookRepository         │    │
│  │  • BookFormat        │  │  • BookReaderRepository   │    │
│  │  • BookContent       │  │  • ProgressRepository     │    │
│  │  • ReadingProgress   │  │  • BookmarkRepository     │    │
│  │  • Bookmark          │  │                           │    │
│  └──────────────────────┘  └──────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

## Fluxo de Dados

### 1. Adicionar Livro
```
User → MainWindow → AddBookUseCase → BookRepository → JsonBookRepository → books.json
```

### 2. Ler Livro
```
User → ReaderWindow → ReadPageUseCase → BookReaderRepository → FormatReader → Arquivo
```

### 3. Salvar Progresso
```
ReaderWindow → UpdateProgressUseCase → ProgressRepository → reading_progress.json
```

## Estrutura de Diretórios

```
ReadCore/
├── pom.xml                                 # Maven configuration
├── README.md                               # Main documentation
├── QUICKSTART.md                          # Quick start guide
├── run.bat / run.sh                       # Run scripts
├── sample-book.txt                        # Sample book
├── data/                                  # Local data storage (created at runtime)
│   ├── books.json                         # Book library
│   ├── reading_progress.json              # Reading positions
│   └── bookmarks.json                     # Saved bookmarks
└── src/
    └── main/
        └── java/
            └── com/
                └── readcore/
                    ├── domain/                    # DOMAIN LAYER
                    │   ├── entities/
                    │   │   ├── Book.java
                    │   │   ├── BookFormat.java
                    │   │   ├── BookContent.java
                    │   │   ├── ReadingProgress.java
                    │   │   └── Bookmark.java
                    │   └── repositories/
                    │       ├── BookRepository.java
                    │       ├── BookReaderRepository.java
                    │       ├── ReadingProgressRepository.java
                    │       └── BookmarkRepository.java
                    │
                    ├── usecases/                  # USE CASES LAYER
                    │   ├── AddBookUseCase.java
                    │   ├── GetAllBooksUseCase.java
                    │   ├── OpenBookUseCase.java
                    │   ├── ReadPageUseCase.java
                    │   ├── UpdateReadingProgressUseCase.java
                    │   ├── GetReadingProgressUseCase.java
                    │   ├── CreateBookmarkUseCase.java
                    │   ├── GetBookmarksUseCase.java
                    │   ├── RemoveBookUseCase.java
                    │   └── SearchBooksUseCase.java
                    │
                    ├── adapters/                  # ADAPTERS LAYER
                    │   ├── persistence/
                    │   │   ├── JsonBookRepository.java
                    │   │   ├── JsonReadingProgressRepository.java
                    │   │   ├── JsonBookmarkRepository.java
                    │   │   └── LocalDateTimeAdapter.java
                    │   └── readers/
                    │       ├── FormatReader.java
                    │       ├── PdfReader.java
                    │       ├── EpubBookReader.java
                    │       ├── TxtReader.java
                    │       └── MultiFormatBookReaderRepository.java
                    │
                    └── framework/                 # FRAMEWORK LAYER
                        ├── Main.java              # Entry point & DI
                        └── ui/
                            ├── MainWindow.java    # Library window
                            └── ReaderWindow.java  # Reader window
```

## Princípios Clean Architecture Aplicados

### 1. Dependency Rule
As dependências apontam sempre para dentro:
- Framework → Adapters → Use Cases → Domain
- Domain não tem dependências externas
- Use Cases dependem apenas do Domain

### 2. Independence
- **Frameworks**: UI pode mudar de Swing para JavaFX sem afetar lógica
- **Database**: Pode trocar JSON por SQL sem afetar Use Cases
- **UI**: Interface independente da lógica de negócio

### 3. Testability
Cada camada pode ser testada isoladamente:
- Domain: Testes unitários puros
- Use Cases: Mock dos repositories
- Adapters: Testes de integração
- Framework: Testes de UI

## Padrões de Design Utilizados

### Repository Pattern
- Abstração do acesso a dados
- Interfaces no Domain, implementações nos Adapters
- Facilita troca de implementação

### Use Case Pattern
- Cada ação do usuário é um Use Case
- Lógica de aplicação isolada
- Fácil de entender e testar

### Strategy Pattern
- MultiFormatBookReaderRepository usa diferentes readers
- FormatReader como estratégia
- Fácil adicionar novos formatos

### Dependency Injection
- Main.java faz DI manual
- Todas as dependências passadas via construtor
- Sem frameworks de DI para manter simplicidade

## Características Técnicas

### Persistência
- **Formato**: JSON
- **Localização**: Pasta `data/`
- **Bibliotecas**: Gson
- **Vantagens**: 
  - Legível por humanos
  - Fácil backup
  - Sem dependência de BD externo

### Leitura de Formatos
| Formato | Biblioteca        | Tipo de Conteúdo |
|---------|------------------|------------------|
| PDF     | Apache PDFBox    | Texto extraído   |
| EPUB    | EPUBLib          | HTML/XHTML       |
| TXT     | BufferedReader   | Texto puro       |
| MOBI    | TxtReader        | Texto puro       |

### Interface Gráfica
- **Framework**: Java Swing
- **Estilo**: Look and Feel do sistema
- **Componentes**:
  - JFrame para janelas
  - JList para biblioteca
  - JTextPane para conteúdo
  - JSlider para navegação

## Extensibilidade

### Adicionar Novo Formato
1. Criar novo `FormatReader` implementando interface
2. Registrar em `MultiFormatBookReaderRepository`
3. Adicionar extensão em `BookFormat` enum

### Adicionar Nova Funcionalidade
1. Criar nova entidade se necessário (Domain)
2. Criar Use Case (Use Cases)
3. Implementar adaptadores se necessário (Adapters)
4. Adicionar UI (Framework)

### Trocar Persistência
1. Criar nova implementação de Repository
2. Atualizar DI em `Main.java`
3. Use Cases não mudam!

## Vantagens da Arquitetura

✅ **Manutenível** - Fácil localizar e modificar código  
✅ **Testável** - Cada camada testável isoladamente  
✅ **Flexível** - Fácil trocar implementações  
✅ **Escalável** - Estrutura suporta crescimento  
✅ **Compreensível** - Organização clara e lógica  

---

Esta arquitetura garante que o ReadCore seja um projeto profissional,
bem estruturado e fácil de manter e expandir no futuro.
