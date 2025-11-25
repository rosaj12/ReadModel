package com.readcore.framework;

import com.readcore.adapters.persistence.JsonBookRepository;
import com.readcore.adapters.persistence.JsonBookmarkRepository;
import com.readcore.adapters.persistence.JsonReadingProgressRepository;
import com.readcore.adapters.readers.MultiFormatBookReaderRepository;
import com.readcore.domain.repositories.BookReaderRepository;
import com.readcore.domain.repositories.BookRepository;
import com.readcore.domain.repositories.BookmarkRepository;
import com.readcore.domain.repositories.ReadingProgressRepository;
import com.readcore.framework.ui.MainWindow;
import com.readcore.usecases.*;

import javax.swing.*;
import java.io.File;

/**
 * Main application entry point
 * Handles dependency injection and application initialization
 */
public class Main {
    private static final String DATA_DIRECTORY = "data";
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
        
        // Ensure data directory exists
        ensureDataDirectory();
        
        // Initialize repositories (Adapters layer)
        BookRepository bookRepository = new JsonBookRepository(DATA_DIRECTORY);
        BookmarkRepository bookmarkRepository = new JsonBookmarkRepository(DATA_DIRECTORY);
        ReadingProgressRepository progressRepository = new JsonReadingProgressRepository(DATA_DIRECTORY);
        BookReaderRepository readerRepository = new MultiFormatBookReaderRepository();
        
        // Initialize use cases (Use Cases layer)
        GetAllBooksUseCase getAllBooksUseCase = new GetAllBooksUseCase(bookRepository);
        AddBookUseCase addBookUseCase = new AddBookUseCase(bookRepository);
        RemoveBookUseCase removeBookUseCase = new RemoveBookUseCase(
                bookRepository, bookmarkRepository, progressRepository);
        SearchBooksUseCase searchBooksUseCase = new SearchBooksUseCase(bookRepository);
        OpenBookUseCase openBookUseCase = new OpenBookUseCase(bookRepository);
        ReadPageUseCase readPageUseCase = new ReadPageUseCase(readerRepository);
        UpdateReadingProgressUseCase updateProgressUseCase = 
                new UpdateReadingProgressUseCase(progressRepository);
        GetReadingProgressUseCase getProgressUseCase = 
                new GetReadingProgressUseCase(progressRepository);
        CreateBookmarkUseCase createBookmarkUseCase = new CreateBookmarkUseCase(bookmarkRepository);
        GetBookmarksUseCase getBookmarksUseCase = new GetBookmarksUseCase(bookmarkRepository);
        
        // Launch UI (Framework layer)
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow(
                    getAllBooksUseCase,
                    addBookUseCase,
                    removeBookUseCase,
                    searchBooksUseCase,
                    openBookUseCase,
                    readPageUseCase,
                    updateProgressUseCase,
                    getProgressUseCase,
                    createBookmarkUseCase,
                    getBookmarksUseCase,
                    readerRepository
            );
            mainWindow.setVisible(true);
        });
    }
    
    private static void ensureDataDirectory() {
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
}
