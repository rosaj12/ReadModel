# ARQUITETURA - ReadCore Android

## 📐 Visão Geral da Arquitetura

ReadCore Android segue os princípios de **Clean Architecture** proposta por Robert C. Martin (Uncle Bob), garantindo separação de responsabilidades, testabilidade e manutenibilidade do código.

## 🏗️ Camadas da Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                     FRAMEWORK LAYER                          │
│  (UI, ViewModels, Android Components, Dependency Injection) │
│                    ↓ depends on ↓                            │
├─────────────────────────────────────────────────────────────┤
│                     ADAPTERS LAYER                           │
│     (Repositories Implementation, Readers, Data Sources)     │
│                    ↓ depends on ↓                            │
├─────────────────────────────────────────────────────────────┤
│                    USE CASES LAYER                           │
│            (Application Business Rules)                      │
│                    ↓ depends on ↓                            │
├─────────────────────────────────────────────────────────────┤
│                     DOMAIN LAYER                             │
│          (Entities, Repository Interfaces)                   │
│                  (NO DEPENDENCIES)                           │
└─────────────────────────────────────────────────────────────┘
```

## 📦 Estrutura de Diretórios

```
android/app/src/main/java/com/readcore/android/
│
├── domain/                          # Camada de Domínio (Innermost)
│   ├── entities/
│   │   └── Entities.kt             # Book, BookFormat, BookContent, etc.
│   └── repositories/
│       └── Repositories.kt         # Interfaces dos repositórios
│
├── usecases/                        # Camada de Casos de Uso
│   └── UseCases.kt                 # AddBook, ReadPage, CreateBookmark, etc.
│
├── adapters/                        # Camada de Adaptadores
│   ├── repositories/
│   │   ├── JsonBookRepository.kt
│   │   ├── JsonReadingProgressRepository.kt
│   │   └── JsonBookmarkRepository.kt
│   └── readers/
│       ├── PdfBookReader.kt
│       ├── EpubBookReader.kt
│       └── TxtBookReader.kt
│
└── framework/                       # Camada de Framework (Outermost)
    ├── ui/
    │   ├── screens/
    │   │   ├── LibraryScreen.kt
    │   │   └── ReaderScreen.kt
    │   ├── theme/
    │   │   ├── Color.kt
    │   │   ├── Theme.kt
    │   │   └── Type.kt
    │   └── Navigation.kt
    ├── viewmodels/
    │   ├── LibraryViewModel.kt
    │   └── ReaderViewModel.kt
    ├── AppContainer.kt              # Dependency Injection
    ├── MainActivity.kt
    └── ReadCoreApplication.kt
```

## 🎯 Responsabilidades de Cada Camada

### 1. Domain Layer (Camada de Domínio)

**Responsabilidades:**
- Definir entidades de negócio
- Definir interfaces de repositórios
- **NENHUMA dependência externa** (nem Android, nem bibliotecas)

**Componentes:**

#### Entidades (Entities.kt)
```kotlin
- Book: Representa um livro digital
- BookFormat: Enum dos formatos suportados
- BookContent: Conteúdo de uma página
- ReadingProgress: Progresso de leitura
- Bookmark: Marcador de página
```

#### Interfaces de Repositórios (Repositories.kt)
```kotlin
- BookRepository: CRUD de livros
- BookReaderRepository: Leitura de páginas
- ReadingProgressRepository: Persistência de progresso
- BookmarkRepository: Gestão de marcadores
```

**Princípios:**
- ✅ Zero dependências externas
- ✅ Puro Kotlin
- ✅ Testável sem Android
- ✅ Reutilizável em outros projetos

### 2. Use Cases Layer (Camada de Casos de Uso)

**Responsabilidades:**
- Implementar regras de negócio da aplicação
- Orquestrar fluxo de dados entre camadas
- Validar entradas do usuário

**Use Cases implementados:**

```kotlin
- AddBookUseCase: Adicionar livro à biblioteca
- GetAllBooksUseCase: Listar todos os livros
- OpenBookUseCase: Abrir livro para leitura
- ReadPageUseCase: Ler página específica
- UpdateReadingProgressUseCase: Atualizar progresso
- GetReadingProgressUseCase: Obter progresso
- CreateBookmarkUseCase: Criar marcador
- GetBookmarksUseCase: Listar marcadores
- RemoveBookUseCase: Remover livro e dados associados
- SearchBooksUseCase: Buscar livros
```

**Princípios:**
- ✅ Depende apenas do Domain Layer
- ✅ Um use case = Uma ação do usuário
- ✅ Contém lógica de negócio
- ✅ Independente de frameworks

### 3. Adapters Layer (Camada de Adaptadores)

**Responsabilidades:**
- Implementar interfaces definidas no Domain
- Adaptar dados externos para entidades de domínio
- Gerenciar I/O (arquivos, banco de dados, rede)

**Componentes:**

#### Repositórios
```kotlin
- JsonBookRepository
  → Implementa BookRepository
  → Usa DataStore para persistência
  
- JsonReadingProgressRepository
  → Implementa ReadingProgressRepository
  → Armazena progresso em JSON
  
- JsonBookmarkRepository
  → Implementa BookmarkRepository
  → Persiste marcadores
```

#### Leitores de Formato
```kotlin
- PdfBookReader
  → Implementa BookReaderRepository
  → Usa PDFBox para ler PDFs
  
- EpubBookReader
  → Implementa BookReaderRepository
  → Usa JSoup para parsear EPUB
  
- TxtBookReader
  → Implementa BookReaderRepository
  → Lê arquivos de texto simples
```

**Princípios:**
- ✅ Implementa interfaces do Domain
- ✅ Pode usar bibliotecas externas
- ✅ Converte dados externos → Entidades
- ✅ Isola detalhes de implementação

### 4. Framework Layer (Camada de Framework)

**Responsabilidades:**
- UI com Jetpack Compose
- Navegação entre telas
- ViewModels (state management)
- Dependency Injection
- Integração com Android Framework

**Componentes:**

#### UI (Jetpack Compose)
```kotlin
- LibraryScreen: Tela da biblioteca
  → Lista de livros
  → Busca
  → Adicionar/remover livros
  
- ReaderScreen: Tela de leitura
  → Exibição de conteúdo
  → Navegação de páginas
  → Marcadores
  → Ajuste de fonte
```

#### ViewModels
```kotlin
- LibraryViewModel
  → Estado da biblioteca (LibraryUiState)
  → Ações: loadBooks, searchBooks, addBook, removeBook
  
- ReaderViewModel
  → Estado da leitura (ReaderUiState)
  → Ações: goToPage, nextPage, previousPage, createBookmark
```

#### Dependency Injection
```kotlin
- AppContainer
  → Manual DI (sem frameworks)
  → Instancia repositórios
  → Instancia use cases
  → Provê readers por formato
```

**Princípios:**
- ✅ Única camada que conhece Android
- ✅ UI declarativa com Compose
- ✅ State management com StateFlow
- ✅ Navegação com Navigation Compose

## 🔄 Fluxo de Dados

### Exemplo: Adicionar um livro

```
[UI] LibraryScreen
  ↓ usuário clica em "+"
[ViewModel] LibraryViewModel.addBook(filePath)
  ↓ chama
[Use Case] AddBookUseCase.execute(filePath)
  ↓ valida e cria Book
[Repository] BookRepository.save(book)
  ↓ serializa
[Adapter] JsonBookRepository → DataStore
  ↓ persiste
[Storage] books.preferences_pb
  ↓ callback success
[Use Case] retorna Book
  ↓ atualiza estado
[ViewModel] recarrega lista
  ↓ emite novo estado
[UI] recompõe com novo livro
```

### Exemplo: Ler uma página

```
[UI] ReaderScreen
  ↓ usuário clica "próxima"
[ViewModel] ReaderViewModel.nextPage()
  ↓ calcula nova página
[ViewModel] ReaderViewModel.goToPage(pageNumber)
  ↓ chama
[Use Case] ReadPageUseCase.execute(pageNumber)
  ↓ valida número
[Repository] BookReaderRepository.getPage(pageNumber)
  ↓ delega para reader específico
[Adapter] PdfBookReader.getPage()
  ↓ usa PDFBox
[Library] PDFTextStripper extrai texto
  ↓ retorna string
[Adapter] cria BookContent
  ↓ retorna
[Use Case] retorna BookContent
  ↓ atualiza estado
[ViewModel] emite novo pageContent
  ↓ também chama
[Use Case] UpdateReadingProgressUseCase
  ↓ salva progresso
[UI] recompõe com novo conteúdo
```

## 🧩 Dependency Rule

**Regra fundamental:** As dependências apontam sempre para dentro (das camadas externas para as internas).

```
Framework → Adapters → Use Cases → Domain
   ✅          ✅           ✅         ❌ (sem dependências)
```

**Proibido:**
- ❌ Domain depender de Use Cases
- ❌ Use Cases depender de Adapters
- ❌ Domain/Use Cases conhecer Android

**Permitido:**
- ✅ Framework usar Use Cases
- ✅ Use Cases usar Domain
- ✅ Adapters implementar interfaces do Domain
- ✅ Framework instanciar Adapters

## 🎨 Padrões de Design Utilizados

### Repository Pattern
```kotlin
// Interface no Domain
interface BookRepository {
    suspend fun save(book: Book)
    suspend fun findById(id: String): Book?
}

// Implementação no Adapter
class JsonBookRepository(context: Context) : BookRepository {
    override suspend fun save(book: Book) {
        // DataStore implementation
    }
}
```

### Dependency Injection (Manual)
```kotlin
class AppContainer(context: Context) {
    val bookRepository = JsonBookRepository(context)
    val addBookUseCase = AddBookUseCase(bookRepository)
}
```

### Strategy Pattern (Readers)
```kotlin
fun getReaderForFormat(format: BookFormat): BookReaderRepository {
    return when (format) {
        BookFormat.PDF -> pdfReader
        BookFormat.EPUB -> epubReader
        BookFormat.TXT -> txtReader
    }
}
```

### MVVM (Model-View-ViewModel)
```kotlin
// Model: Domain entities
data class Book(...)

// ViewModel: State + Actions
class LibraryViewModel {
    val uiState: StateFlow<LibraryUiState>
    fun addBook(...)
}

// View: Composable UI
@Composable
fun LibraryScreen(
    books: List<Book>,
    onAddBook: (String) -> Unit
)
```

## 🧪 Testabilidade

### Domain Layer
```kotlin
// Testável sem Android
@Test
fun `book should calculate progress correctly`() {
    val progress = ReadingProgress("id", totalPages = 100)
    progress.updateProgress(50)
    assertEquals(50.0, progress.percentageComplete)
}
```

### Use Cases
```kotlin
// Mock repositories
@Test
fun `addBook should save to repository`() = runTest {
    val mockRepo = mockk<BookRepository>()
    val useCase = AddBookUseCase(mockRepo)
    
    useCase.execute("/path/to/book.pdf")
    
    verify { mockRepo.save(any()) }
}
```

### ViewModels
```kotlin
// Test state changes
@Test
fun `loadBooks should update state`() = runTest {
    val viewModel = LibraryViewModel(mockContainer)
    viewModel.loadBooks()
    
    val state = viewModel.uiState.value
    assertTrue(state.books.isNotEmpty())
}
```

## 📊 Comparação com Outras Arquiteturas

### vs MVC (Model-View-Controller)
- ✅ Melhor separação de responsabilidades
- ✅ Mais testável
- ✅ Independente de frameworks

### vs MVP (Model-View-Presenter)
- ✅ Menos acoplamento
- ✅ Use cases reutilizáveis
- ✅ Mais escalável

### vs MVI (Model-View-Intent)
- ✅ Mais flexível
- ✅ Menor curva de aprendizado
- ➖ Menos unidirecional

## 🚀 Benefícios da Clean Architecture

1. **Independência de Frameworks**
   - Domain e Use Cases não conhecem Android
   - Pode migrar para KMP (Kotlin Multiplatform)

2. **Testabilidade**
   - Testes unitários sem emulador
   - Mock fácil de dependências

3. **Independência de UI**
   - Pode trocar Compose por Views
   - Lógica permanece inalterada

4. **Independência de Banco de Dados**
   - Pode trocar DataStore por Room
   - Apenas Adapter muda

5. **Independência de Bibliotecas**
   - PDFBox pode ser substituído
   - Use cases não mudam

6. **Escalabilidade**
   - Adicionar features não quebra existentes
   - Fácil de manter e evoluir

## 📝 Princípios SOLID Aplicados

- **S**ingle Responsibility: Cada classe tem uma responsabilidade
- **O**pen/Closed: Aberto para extensão, fechado para modificação
- **L**iskov Substitution: Implementações podem ser substituídas
- **I**nterface Segregation: Interfaces específicas e focadas
- **D**ependency Inversion: Depende de abstrações, não implementações

## 🔮 Evolução Futura

Possíveis extensões mantendo a arquitetura:

1. **Camada de Dados Remota**
   ```kotlin
   - RemoteBookRepository (sincronização cloud)
   - NetworkModule (API calls)
   ```

2. **Casos de Uso Avançados**
   ```kotlin
   - SyncBooksUseCase
   - ExportAnnotationsUseCase
   - ShareQuoteUseCase
   ```

3. **Múltiplas UIs**
   ```kotlin
   - Wear OS module
   - TV module
   - Desktop module (Compose Multiplatform)
   ```

4. **Persistência Híbrida**
   ```kotlin
   - Room para dados estruturados
   - DataStore para preferências
   - File system para livros
   ```

Todas essas extensões podem ser feitas sem alterar o núcleo (Domain + Use Cases)!
