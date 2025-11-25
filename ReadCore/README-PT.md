# ReadCore - Leitor de Livros Digitais

![Java](https://img.shields.io/badge/Java-11+-blue.svg)
![Clean Architecture](https://img.shields.io/badge/Arquitetura-Clean-green.svg)
![License](https://img.shields.io/badge/Licença-MIT-yellow.svg)

ReadCore é um leitor de livros digitais limpo e offline, construído com princípios de Clean Architecture em Java. Oferece uma interface simples e intuitiva para ler múltiplos formatos de livros completamente offline.

## Recursos

### 📚 Suporte Multi-Formato
- **PDF** - Suporte completo a documentos PDF
- **EPUB** - Leitura completa de EPUB
- **TXT** - Arquivos de texto simples
- **MOBI** - Formato Mobipocket

### 🎯 Funcionalidades Principais
- ✅ **Leitura Offline** - Funciona completamente offline, sem necessidade de internet
- ✅ **Gerenciamento de Biblioteca** - Organize sua coleção de livros digitais
- ✅ **Progresso de Leitura** - Salva automaticamente sua posição em cada livro
- ✅ **Marcadores** - Marque páginas importantes com notas opcionais
- ✅ **Busca** - Encontre livros por título ou autor
- ✅ **Interface Limpa** - Experiência de leitura simples e sem distrações
- ✅ **Controle de Fonte** - Ajuste o tamanho do texto para leitura confortável
- ✅ **Navegação** - Navegação fácil entre páginas com slider e botões

## Arquitetura

ReadCore segue os princípios de **Clean Architecture** com clara separação de responsabilidades:

```
ReadCore/
├── domain/                 # Regras de Negócio Empresariais
│   ├── entities/          # Book, Bookmark, ReadingProgress
│   └── repositories/      # Interfaces de repositório
├── usecases/              # Regras de Negócio da Aplicação
│   └── *UseCase.java      # Implementações de casos de uso
├── adapters/              # Adaptadores de Interface
│   ├── persistence/       # Repositórios baseados em JSON
│   └── readers/           # Leitores específicos de formato (PDF, EPUB, TXT)
└── framework/             # Frameworks & Drivers
    ├── ui/               # Componentes de UI Swing
    └── Main.java         # Ponto de entrada da aplicação
```

### Dependências entre Camadas
- **Domain** - Sem dependências, lógica de negócio pura
- **Use Cases** - Depende apenas do Domain
- **Adapters** - Implementa interfaces do Domain
- **Framework** - Depende de todas as camadas internas

## Começando

### Pré-requisitos
- Java 11 ou superior
- Maven 3.6+

### Compilando a Aplicação

```bash
cd ReadCore
mvn clean package
```

### Executando a Aplicação

```bash
java -jar target/readcore-1.0.0.jar
```

Ou simplesmente:

```bash
mvn exec:java -Dexec.mainClass="com.readcore.framework.Main"
```

## Uso

### Adicionando Livros
1. Clique em **"Add Book"** ou use **File → Add Book**
2. Selecione seu arquivo de livro (PDF, EPUB, TXT, ou MOBI)
3. O livro aparecerá em sua biblioteca

### Lendo Livros
1. Dê um duplo clique em um livro na biblioteca ou selecione e clique em **"Open Book"**
2. Navegue usando:
   - Botões **Previous/Next**
   - **Slider de página**
   - Menu **Navigation → Go to Page**
3. Sua posição de leitura é salva automaticamente

### Marcadores
- **Adicionar Marcador**: Clique em **Bookmark → Add Bookmark** enquanto lê
- **Ver Marcadores**: Clique em **Navigation → View Bookmarks**
- **Ir para Marcador**: Dê duplo clique em um marcador para pular para aquela página

### Busca
- Digite um termo de busca no campo de busca na janela principal
- A busca funciona tanto para títulos quanto autores de livros

## Armazenamento de Dados

ReadCore armazena todos os dados localmente em formato JSON:

```
ReadCore/
└── data/
    ├── books.json              # Biblioteca de livros
    ├── reading_progress.json   # Posições de leitura
    └── bookmarks.json          # Marcadores salvos
```

Todos os dados são armazenados offline e permanecem no seu computador.

## Detalhes Técnicos

### Dependências
- **Apache PDFBox** - Renderização de PDF e extração de texto
- **EPUBLib** - Parsing de arquivos EPUB
- **Gson** - Serialização JSON
- **SLF4J** - Framework de logging
- **Java Swing** - Interface do usuário

### Formatos Suportados

| Formato | Extensão | Extração de Texto | Suporte HTML |
|---------|----------|-------------------|--------------|
| PDF     | .pdf     | ✅                | ❌           |
| EPUB    | .epub    | ✅                | ✅           |
| TXT     | .txt     | ✅                | ❌           |
| MOBI    | .mobi    | ✅                | ❌           |

## Desenvolvimento

### Estrutura do Projeto

O projeto segue Clean Architecture com estes componentes-chave:

**Camada Domain:**
- `Book` - Entidade principal de livro
- `BookFormat` - Enumeração de formatos suportados
- `ReadingProgress` - Rastreia posição de leitura
- `Bookmark` - Marcadores de página salvos
- `BookContent` - Representação de conteúdo de página

**Use Cases:**
- `AddBookUseCase` - Adicionar livros à biblioteca
- `OpenBookUseCase` - Abrir um livro para leitura
- `ReadPageUseCase` - Ler páginas específicas
- `CreateBookmarkUseCase` - Criar marcadores
- `UpdateReadingProgressUseCase` - Salvar posição de leitura

**Adapters:**
- `JsonBookRepository` - Persistir livros
- `MultiFormatBookReaderRepository` - Ler múltiplos formatos
- `PdfReader`, `EpubBookReader`, `TxtReader` - Leitores específicos de formato

**Framework:**
- `MainWindow` - UI de gerenciamento de biblioteca
- `ReaderWindow` - UI de leitura de livros
- `Main` - Bootstrap da aplicação

### Testando

Execute testes com:
```bash
mvn test
```

## Roadmap

Melhorias futuras:
- [ ] Destaques e anotações
- [ ] Busca de texto completo dentro dos livros
- [ ] Modo noturno/temas
- [ ] Exportar marcadores e notas
- [ ] Estatísticas de leitura
- [ ] Coleções e categorias

## Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para enviar pull requests.

## Licença

Este projeto está licenciado sob a Licença MIT.

## Agradecimentos

- Construído com princípios de Clean Architecture por Robert C. Martin
- Usa Apache PDFBox para suporte a PDF
- Suporte EPUB fornecido por EPUBLib

---

**ReadCore** - Leitura Limpa, Simples e Offline
