package com.readcore.usecases;

import com.readcore.domain.entities.Book;
import com.readcore.domain.entities.BookFormat;
import com.readcore.domain.repositories.BookRepository;

import java.io.File;
import java.util.UUID;

/**
 * Use case for adding a book to the library
 */
public class AddBookUseCase {
    private final BookRepository bookRepository;
    
    public AddBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public Book execute(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }
        
        BookFormat format = BookFormat.fromFilename(file.getName());
        String title = extractTitle(file.getName());
        String id = UUID.randomUUID().toString();
        
        Book book = new Book(id, title, "Unknown Author", filePath, format);
        book.setFileSize(file.length());
        
        bookRepository.save(book);
        return book;
    }
    
    private String extractTitle(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
            return filename.substring(0, lastDot);
        }
        return filename;
    }
}
