# ReadCore - Quick Start Guide

## Início Rápido (Portuguese)

### Executar o Aplicativo

O projeto já inclui um **arquivo JAR pronto** na pasta `target/`:

#### Opção 1: Executar diretamente
```bash
java -jar target/readcore-1.0.0.jar
```

#### Opção 2: Usar script (Windows)
```bash
start.bat
```

#### Opção 3: Usar script (Linux/Mac)
```bash
chmod +x start.sh
./start.sh
```

### Compilar e Executar (se necessário)

Se você modificou o código ou quer recompilar:

#### No Windows:
```bash
run.bat
```

#### No Linux/Mac:
```bash
chmod +x run.sh
./run.sh
```

Ou manualmente:
```bash
mvn clean package
java -jar target/readcore-1.0.0.jar
```

### Primeiro Uso

1. **Adicionar um Livro**
   - Clique em "Add Book" na janela principal
   - Selecione um arquivo (PDF, EPUB, TXT, ou MOBI)
   - O livro será adicionado à sua biblioteca
   - Um livro de exemplo está incluído: `sample-book.txt`

2. **Ler um Livro**
   - Dê um duplo clique no livro na lista
   - Ou selecione e clique em "Open Book"
   - Use os botões Previous/Next para navegar
   - Use o slider para pular páginas rapidamente

3. **Adicionar Marcadores**
   - Durante a leitura, vá em Bookmark → Add Bookmark
   - Adicione uma nota opcional
   - Visualize seus marcadores em Navigation → View Bookmarks

4. **Buscar Livros**
   - Digite no campo de busca na janela principal
   - Busque por título ou autor

## Características Principais

✅ **Totalmente Offline** - Funciona sem internet
✅ **Multi-formato** - PDF, EPUB, TXT, MOBI
✅ **Progresso Automático** - Salva sua posição automaticamente
✅ **Marcadores** - Marque páginas importantes
✅ **Interface Limpa** - Leitura sem distrações
✅ **Controle de Fonte** - Ajuste o tamanho do texto

## Estrutura de Dados

Todos os dados são salvos localmente em:
```
ReadCore/
└── data/
    ├── books.json              # Biblioteca de livros
    ├── reading_progress.json   # Progresso de leitura
    └── bookmarks.json          # Marcadores salvos
```

## Formatos Suportados

| Formato | Extensão | Status |
|---------|----------|--------|
| PDF     | .pdf     | ✅     |
| EPUB    | .epub    | ✅     |
| TXT     | .txt     | ✅     |
| MOBI    | .mobi    | ✅     |

## Atalhos e Dicas

- **Duplo clique** em um livro para abri-lo rapidamente
- **Duplo clique** em um marcador para ir para aquela página
- Use **View → Increase/Decrease Font Size** para conforto de leitura
- A busca funciona para títulos e autores
- Seu progresso é salvo automaticamente

## Arquitetura Clean Architecture

O projeto segue os princípios de Clean Architecture:

```
Domain (Entidades de Negócio)
    ↑
Use Cases (Regras de Aplicação)
    ↑
Adapters (Interface Adapters)
    ↑
Framework (UI e Infraestrutura)
```

### Camadas:

1. **Domain** - Entidades puras sem dependências
   - Book, Bookmark, ReadingProgress
   - Interfaces de repositórios

2. **Use Cases** - Lógica de aplicação
   - AddBookUseCase, ReadPageUseCase, etc.
   - Dependem apenas do Domain

3. **Adapters** - Implementações de interfaces
   - Repositórios JSON para persistência
   - Leitores de formato (PDF, EPUB, TXT)

4. **Framework** - UI e infraestrutura
   - Interface Swing
   - Injeção de dependências
   - Ponto de entrada

## Solução de Problemas

### Erro ao abrir livro
- Verifique se o arquivo existe
- Verifique se o formato é suportado
- Tente adicionar o livro novamente

### Build falha
- Verifique se tem Java 11+ instalado: `java -version`
- Verifique se tem Maven instalado: `mvn -version`
- Execute `mvn clean install`

### Livros não aparecem
- Verifique a pasta `data/books.json`
- Tente adicionar um livro novamente
- O arquivo é criado automaticamente

## Desenvolvimento

### Executar em modo de desenvolvimento:
```bash
mvn exec:java -Dexec.mainClass="com.readcore.framework.Main"
```

### Compilar sem executar:
```bash
mvn clean package
```

### Executar testes:
```bash
mvn test
```

## Tecnologias Utilizadas

- **Java 11+** - Linguagem principal
- **Maven** - Gerenciamento de dependências
- **Apache PDFBox** - Suporte a PDF
- **EPUBLib** - Leitura de EPUB
- **Gson** - Serialização JSON
- **Java Swing** - Interface gráfica
- **SLF4J** - Logging

## Próximos Passos

Recursos planejados:
- [ ] Destaques e anotações
- [ ] Busca de texto completo dentro dos livros
- [ ] Modo noturno/temas
- [ ] Exportar marcadores e notas
- [ ] Estatísticas de leitura
- [ ] Coleções e categorias

## Suporte

Para problemas ou sugestões, abra uma issue no repositório.

---

**ReadCore** - Leitura Limpa, Simples e Offline
