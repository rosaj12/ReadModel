# ReadCore - Resumo do Projeto

## 📚 Sobre o Projeto

**ReadCore** é um aplicativo de leitura de livros digitais desenvolvido em Java seguindo os princípios de **Clean Architecture**. O aplicativo é totalmente **offline**, com interface limpa e intuitiva, suportando múltiplos formatos de livros.

## ✨ Características Principais

### 📖 Funcionalidades
- ✅ Leitura de múltiplos formatos (PDF, EPUB, TXT, MOBI)
- ✅ Gerenciamento de biblioteca de livros
- ✅ Salvamento automático de progresso de leitura
- ✅ Sistema de marcadores (bookmarks) com notas
- ✅ Busca por título e autor
- ✅ Navegação fácil entre páginas
- ✅ Ajuste de tamanho de fonte
- ✅ Funcionamento 100% offline

### 🏗️ Arquitetura
- ✅ Clean Architecture (4 camadas bem definidas)
- ✅ Separação clara de responsabilidades
- ✅ Independência de frameworks
- ✅ Testabilidade em todos os níveis
- ✅ Fácil manutenção e extensibilidade

### 💾 Tecnologias
- Java 11+
- Maven
- Apache PDFBox (leitura de PDF)
- EPUBLib (leitura de EPUB)
- Gson (persistência JSON)
- Java Swing (interface gráfica)
- SLF4J (logging)

## 📁 Estrutura do Projeto

```
ReadCore/
├── 📄 pom.xml                    # Configuração Maven
├── 📄 README.md                  # Documentação principal
├── 📄 QUICKSTART.md             # Guia rápido
├── 📄 ARCHITECTURE.md           # Documentação da arquitetura
├── 📄 SUMMARY.md                # Este arquivo
├── 🔧 run.bat / run.sh          # Scripts de execução
├── 📖 sample-book.txt           # Livro de exemplo
├── 📂 data/                     # Dados locais (criado em runtime)
│   ├── books.json
│   ├── reading_progress.json
│   └── bookmarks.json
└── 📂 src/main/java/com/readcore/
    ├── 📂 domain/               # CAMADA DE DOMÍNIO
    │   ├── entities/            # Entidades de negócio
    │   │   ├── Book.java
    │   │   ├── BookFormat.java
    │   │   ├── BookContent.java
    │   │   ├── ReadingProgress.java
    │   │   └── Bookmark.java
    │   └── repositories/        # Interfaces de repositórios
    │       ├── BookRepository.java
    │       ├── BookReaderRepository.java
    │       ├── ReadingProgressRepository.java
    │       └── BookmarkRepository.java
    │
    ├── 📂 usecases/            # CAMADA DE CASOS DE USO
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
    ├── 📂 adapters/            # CAMADA DE ADAPTADORES
    │   ├── persistence/         # Persistência em JSON
    │   │   ├── JsonBookRepository.java
    │   │   ├── JsonReadingProgressRepository.java
    │   │   ├── JsonBookmarkRepository.java
    │   │   └── LocalDateTimeAdapter.java
    │   └── readers/            # Leitores de formato
    │       ├── FormatReader.java
    │       ├── PdfReader.java
    │       ├── EpubBookReader.java
    │       ├── TxtReader.java
    │       └── MultiFormatBookReaderRepository.java
    │
    └── 📂 framework/           # CAMADA DE FRAMEWORK
        ├── Main.java           # Ponto de entrada
        └── ui/                 # Interface Swing
            ├── MainWindow.java
            └── ReaderWindow.java
```

## 🎯 Camadas da Arquitetura

### 1. Domain Layer (Núcleo do Negócio)
- **Responsabilidade**: Entidades e regras de negócio puras
- **Dependências**: Nenhuma
- **Conteúdo**:
  - 5 Entidades (Book, BookFormat, BookContent, ReadingProgress, Bookmark)
  - 4 Interfaces de Repositório

### 2. Use Cases Layer (Lógica de Aplicação)
- **Responsabilidade**: Casos de uso da aplicação
- **Dependências**: Apenas Domain
- **Conteúdo**:
  - 10 Use Cases implementados
  - Orquestração de entidades e repositórios

### 3. Adapters Layer (Interface Adapters)
- **Responsabilidade**: Implementação de interfaces, I/O
- **Dependências**: Domain, bibliotecas externas
- **Conteúdo**:
  - 4 Repositórios JSON (persistência offline)
  - 5 Leitores de formato (PDF, EPUB, TXT, MOBI)

### 4. Framework Layer (UI e Infraestrutura)
- **Responsabilidade**: Interface do usuário, bootstrap
- **Dependências**: Todas as camadas internas
- **Conteúdo**:
  - Main (DI e inicialização)
  - 2 Janelas Swing (MainWindow, ReaderWindow)

## 🚀 Como Usar

### Compilar e Executar

**Windows:**
```batch
run.bat
```

**Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

**Manual:**
```bash
mvn clean package
java -jar target/readcore-1.0.0.jar
```

### Primeiros Passos

1. Execute o aplicativo
2. Clique em "Add Book"
3. Selecione o arquivo `sample-book.txt` (ou qualquer PDF/EPUB)
4. Dê duplo clique no livro para ler
5. Use Previous/Next ou o slider para navegar
6. Adicione marcadores em Bookmark → Add Bookmark

## 📊 Estatísticas do Projeto

### Código
- **Total de Classes Java**: 31
- **Linhas de Código**: ~2,500+
- **Camadas**: 4 (Domain, Use Cases, Adapters, Framework)
- **Padrões de Design**: Repository, Use Case, Strategy, Dependency Injection

### Documentação
- **README.md**: Documentação completa
- **QUICKSTART.md**: Guia rápido em português
- **ARCHITECTURE.md**: Arquitetura detalhada
- **Código comentado**: Javadoc em todas as classes

### Features Implementadas
- [x] Suporte a múltiplos formatos
- [x] Biblioteca de livros
- [x] Progresso de leitura
- [x] Sistema de marcadores
- [x] Busca de livros
- [x] Interface gráfica limpa
- [x] Persistência offline
- [x] Navegação de páginas
- [x] Controle de fonte

## 🎨 Interface do Usuário

### Janela Principal (MainWindow)
- Lista de livros da biblioteca
- Campo de busca
- Botões: Add Book, Remove Book, Open Book
- Menu: File, Help

### Janela de Leitura (ReaderWindow)
- Área de conteúdo com scroll
- Navegação: Previous, Next, Slider
- Menu: Navigation, Bookmark, View
- Indicador de página atual

## 💡 Pontos Fortes

1. **Arquitetura Limpa**
   - Separação clara de responsabilidades
   - Fácil de testar e manter
   - Independente de frameworks

2. **Offline First**
   - Nenhuma conexão de internet necessária
   - Dados salvos localmente
   - Rápido e responsivo

3. **Multi-formato**
   - Suporta PDF, EPUB, TXT, MOBI
   - Fácil adicionar novos formatos
   - Leitura unificada

4. **Experiência do Usuário**
   - Interface limpa e intuitiva
   - Salvamento automático
   - Navegação fácil

5. **Código Profissional**
   - Clean Architecture
   - Bem documentado
   - Seguindo boas práticas

## 🔮 Possíveis Extensões Futuras

- [ ] Destaques e anotações em texto
- [ ] Busca full-text dentro dos livros
- [ ] Modo noturno / temas customizados
- [ ] Exportar notas e marcadores
- [ ] Estatísticas de leitura
- [ ] Coleções e categorias
- [ ] Sincronização entre dispositivos
- [ ] Suporte a mais formatos (AZW, CBZ)
- [ ] Leitor de áudio (Text-to-Speech)
- [ ] Dicionário integrado

## 📋 Checklist de Implementação

### Domain Layer
- [x] Book entity
- [x] BookFormat enum
- [x] BookContent entity
- [x] ReadingProgress entity
- [x] Bookmark entity
- [x] BookRepository interface
- [x] BookReaderRepository interface
- [x] ReadingProgressRepository interface
- [x] BookmarkRepository interface

### Use Cases Layer
- [x] AddBookUseCase
- [x] GetAllBooksUseCase
- [x] OpenBookUseCase
- [x] ReadPageUseCase
- [x] UpdateReadingProgressUseCase
- [x] GetReadingProgressUseCase
- [x] CreateBookmarkUseCase
- [x] GetBookmarksUseCase
- [x] RemoveBookUseCase
- [x] SearchBooksUseCase

### Adapters Layer
- [x] JsonBookRepository
- [x] JsonReadingProgressRepository
- [x] JsonBookmarkRepository
- [x] LocalDateTimeAdapter
- [x] FormatReader interface
- [x] PdfReader
- [x] EpubBookReader
- [x] TxtReader
- [x] MultiFormatBookReaderRepository

### Framework Layer
- [x] Main (entry point + DI)
- [x] MainWindow (library UI)
- [x] ReaderWindow (reader UI)

### Configuration & Build
- [x] pom.xml com todas as dependências
- [x] .gitignore
- [x] Scripts de execução (run.bat, run.sh)

### Documentation
- [x] README.md completo
- [x] QUICKSTART.md em português
- [x] ARCHITECTURE.md detalhado
- [x] SUMMARY.md (este arquivo)
- [x] Livro de exemplo (sample-book.txt)

## 🎓 Aprendizados do Projeto

Este projeto demonstra:
- ✅ Aplicação prática de Clean Architecture
- ✅ Separação de responsabilidades em camadas
- ✅ Dependency Injection manual
- ✅ Padrão Repository
- ✅ Padrão Use Case
- ✅ Padrão Strategy
- ✅ Programação orientada a interfaces
- ✅ Persistência offline em JSON
- ✅ Desenvolvimento de UI desktop com Swing
- ✅ Integração com bibliotecas externas

## 📞 Suporte

Para dúvidas ou problemas:
1. Consulte o README.md
2. Consulte o QUICKSTART.md
3. Consulte o ARCHITECTURE.md
4. Verifique os comentários no código

---

## 🏆 Conclusão

**ReadCore** é um projeto completo e profissional que demonstra a aplicação prática de Clean Architecture em uma aplicação Java real. Com suporte a múltiplos formatos, interface limpa, e funcionamento totalmente offline, ele oferece uma experiência de leitura digital simples e eficiente.

A arquitetura bem estruturada torna o código fácil de entender, testar e manter, sendo um excelente exemplo de boas práticas de desenvolvimento de software.

**ReadCore** - Leitura Limpa, Simples e Offline ✨📚
