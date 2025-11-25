# ReadCore - Digital Book Reader

![Java](https://img.shields.io/badge/Java-11+-blue.svg)
![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-green.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

ReadCore is a clean, offline digital book reader built with Clean Architecture principles in Java. It provides a simple and intuitive interface for reading multiple book formats completely offline.

## Features

### 📚 Multi-Format Support
- **PDF** - Full PDF document support
- **EPUB** - Complete EPUB reading
- **TXT** - Plain text files
- **MOBI** - Mobipocket format

### 🎯 Core Functionality
- ✅ **Offline Reading** - Works completely offline, no internet required
- ✅ **Library Management** - Organize your digital book collection
- ✅ **Reading Progress** - Automatically saves your position in each book
- ✅ **Bookmarks** - Mark important pages with optional notes
- ✅ **Search** - Find books by title or author
- ✅ **Clean Interface** - Simple, distraction-free reading experience
- ✅ **Font Control** - Adjust text size for comfortable reading
- ✅ **Navigation** - Easy page navigation with slider and buttons

## Architecture

ReadCore follows **Clean Architecture** principles with clear separation of concerns:

```
ReadCore/
├── domain/                 # Enterprise Business Rules
│   ├── entities/          # Book, Bookmark, ReadingProgress
│   └── repositories/      # Repository interfaces
├── usecases/              # Application Business Rules
│   └── *UseCase.java      # Use case implementations
├── adapters/              # Interface Adapters
│   ├── persistence/       # JSON-based repositories
│   └── readers/           # Format-specific readers (PDF, EPUB, TXT)
└── framework/             # Frameworks & Drivers
    ├── ui/               # Swing UI components
    └── Main.java         # Application entry point
```

### Layer Dependencies
- **Domain** - No dependencies, pure business logic
- **Use Cases** - Depends only on Domain
- **Adapters** - Implements Domain interfaces
- **Framework** - Depends on all inner layers

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Building the Application

```bash
cd ReadCore
mvn clean package
```

### Running the Application

```bash
java -jar target/readcore-1.0.0.jar
```

Or simply:

```bash
mvn exec:java -Dexec.mainClass="com.readcore.framework.Main"
```

## Usage

### Adding Books
1. Click **"Add Book"** or use **File → Add Book**
2. Select your book file (PDF, EPUB, TXT, or MOBI)
3. The book will appear in your library

### Reading Books
1. Double-click a book in the library or select and click **"Open Book"**
2. Navigate using:
   - **Previous/Next** buttons
   - **Page slider**
   - **Navigation → Go to Page** menu
3. Your reading position is automatically saved

### Bookmarks
- **Add Bookmark**: Click **Bookmark → Add Bookmark** while reading
- **View Bookmarks**: Click **Navigation → View Bookmarks**
- **Go to Bookmark**: Double-click a bookmark to jump to that page

### Search
- Enter a search term in the search field on the main window
- Search works for both book titles and authors

## Data Storage

ReadCore stores all data locally in JSON format:

```
ReadCore/
└── data/
    ├── books.json              # Book library
    ├── reading_progress.json   # Reading positions
    └── bookmarks.json          # Saved bookmarks
```

All data is stored offline and remains on your computer.

## Technical Details

### Dependencies
- **Apache PDFBox** - PDF rendering and text extraction
- **EPUBLib** - EPUB file parsing
- **Gson** - JSON serialization
- **SLF4J** - Logging framework
- **Java Swing** - User interface

### Supported Formats

| Format | Extension | Text Extraction | HTML Support |
|--------|-----------|----------------|--------------|
| PDF    | .pdf      | ✅             | ❌           |
| EPUB   | .epub     | ✅             | ✅           |
| TXT    | .txt      | ✅             | ❌           |
| MOBI   | .mobi     | ✅             | ❌           |

## Development

### Project Structure

The project follows Clean Architecture with these key components:

**Domain Layer:**
- `Book` - Core book entity
- `BookFormat` - Supported format enumeration
- `ReadingProgress` - Tracks reading position
- `Bookmark` - Saved page markers
- `BookContent` - Page content representation

**Use Cases:**
- `AddBookUseCase` - Add books to library
- `OpenBookUseCase` - Open a book for reading
- `ReadPageUseCase` - Read specific pages
- `CreateBookmarkUseCase` - Create bookmarks
- `UpdateReadingProgressUseCase` - Save reading position

**Adapters:**
- `JsonBookRepository` - Persist books
- `MultiFormatBookReaderRepository` - Read multiple formats
- `PdfReader`, `EpubBookReader`, `TxtReader` - Format-specific readers

**Framework:**
- `MainWindow` - Library management UI
- `ReaderWindow` - Book reading UI
- `Main` - Application bootstrap

### Testing

Run tests with:
```bash
mvn test
```

## Roadmap

Future enhancements:
- [ ] Highlighting and annotations
- [ ] Full-text search within books
- [ ] Night mode/themes
- [ ] Export bookmarks and notes
- [ ] Reading statistics
- [ ] Collections and categories

## Contributing

Contributions are welcome! Please feel free to submit pull requests.

## License

This project is licensed under the MIT License.

## Acknowledgments

- Built with Clean Architecture principles by Robert C. Martin
- Uses Apache PDFBox for PDF support
- EPUB support provided by EPUBLib

---

**ReadCore** - Clean, Simple, Offline Reading
