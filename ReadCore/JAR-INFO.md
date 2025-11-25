# ReadCore - Arquivo JAR Executável

## 📦 Localização do JAR

O arquivo JAR executável está localizado em:
```
ReadCore/target/readcore-1.0.0.jar
```

**Tamanho**: ~5.2 MB (inclui todas as dependências)

## 🚀 Como Executar

### Método 1: Comando Direto
```bash
java -jar target/readcore-1.0.0.jar
```

### Método 2: Script de Inicialização

**Windows:**
```bash
start.bat
```

**Linux/Mac:**
```bash
chmod +x start.sh
./start.sh
```

### Método 3: Duplo Clique (Windows)
1. Navegue até a pasta `target/`
2. Dê duplo clique em `readcore-1.0.0.jar`
3. O aplicativo iniciará automaticamente

## 📋 Pré-requisitos

- **Java 11 ou superior** instalado no sistema
- Verificar instalação: `java -version`

## 🔧 Detalhes do JAR

### Conteúdo do JAR
O JAR foi criado com **Maven Shade Plugin** e inclui:

✅ Todas as classes compiladas do ReadCore  
✅ Apache PDFBox (suporte a PDF)  
✅ JSoup (parsing HTML/EPUB)  
✅ Gson (serialização JSON)  
✅ SLF4J (logging)  
✅ Todas as dependências necessárias  

### Estrutura Interna
```
readcore-1.0.0.jar
├── com/readcore/
│   ├── domain/
│   ├── usecases/
│   ├── adapters/
│   └── framework/
├── org/apache/pdfbox/
├── org/jsoup/
├── com/google/gson/
├── org/slf4j/
└── META-INF/
    └── MANIFEST.MF (Main-Class: com.readcore.framework.Main)
```

## 🎯 Características do JAR

- **Tipo**: Uber JAR (Fat JAR)
- **Main Class**: `com.readcore.framework.Main`
- **Totalmente autônomo**: Não requer dependências externas
- **Multiplataforma**: Funciona em Windows, Linux e Mac
- **Offline**: Nenhuma conexão à internet necessária

## 📂 Estrutura de Dados

Ao executar, o aplicativo criará automaticamente a pasta `data/`:

```
ReadCore/
├── target/
│   └── readcore-1.0.0.jar  ← Arquivo JAR
├── data/                   ← Criado automaticamente
│   ├── books.json
│   ├── reading_progress.json
│   └── bookmarks.json
└── start.bat / start.sh    ← Scripts de execução
```

## 🔨 Recompilar o JAR

Se você modificou o código-fonte:

```bash
mvn clean package
```

Isso irá:
1. Limpar a pasta `target/`
2. Compilar todo o código
3. Executar testes (se houver)
4. Criar o JAR com todas as dependências

## ⚡ Execução Rápida

Para desenvolvedores que não querem criar o JAR toda vez:

```bash
mvn exec:java -Dexec.mainClass="com.readcore.framework.Main"
```

## 🐛 Solução de Problemas

### "java: command not found"
- Java não está instalado ou não está no PATH
- Solução: Instale Java 11+ e configure o PATH

### "Could not find or load main class"
- JAR corrompido ou incompleto
- Solução: Recompile com `mvn clean package`

### "UnsupportedClassVersionError"
- Versão do Java muito antiga
- Solução: Atualize para Java 11 ou superior

### Aplicativo não abre
- Verifique se você está no diretório correto
- Execute: `java -jar target/readcore-1.0.0.jar` manualmente
- Verifique a saída do console para erros

## 📊 Informações Técnicas

### Processo de Build
```
Código Fonte → Compilação → Testes → Empacotamento → JAR
     (src)    →   (javac)  → (junit) →   (shade)   → (target)
```

### Plugins Maven Utilizados
- **maven-compiler-plugin**: Compilação do código Java
- **maven-jar-plugin**: Criação do JAR básico
- **maven-shade-plugin**: Criação do uber JAR com dependências

### Manifest do JAR
```
Main-Class: com.readcore.framework.Main
Implementation-Title: ReadCore
Implementation-Version: 1.0.0
```

## 🎓 Para Distribuição

### Compartilhar o Aplicativo
Para distribuir o ReadCore:

1. Copie apenas o arquivo: `target/readcore-1.0.0.jar`
2. Forneça instruções: "Execute com `java -jar readcore-1.0.0.jar`"
3. Requisito mínimo: Java 11+

### Criar Instalador (Opcional)
Para criar um instalador nativo:

**Windows:**
- Use jpackage (Java 14+)
- Ou ferramentas como Launch4j, Inno Setup

**Linux:**
- Use jpackage para criar .deb ou .rpm

**Mac:**
- Use jpackage para criar .dmg ou .pkg

## 📝 Notas Importantes

⚠️ O JAR é autossuficiente e não precisa do código-fonte para executar  
⚠️ Todos os dados são salvos na pasta `data/` no mesmo diretório  
⚠️ Não delete a pasta `data/` se quiser manter seus livros e progresso  
✅ Você pode mover o JAR para qualquer local (criará nova pasta `data/`)  

---

**ReadCore v1.0.0** - Pronto para Usar! 🚀
