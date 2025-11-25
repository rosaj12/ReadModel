package com.readcore.adapters.readers;

import com.readcore.domain.entities.BookContent;
import com.readcore.domain.entities.BookFormat;
import com.readcore.domain.repositories.BookReaderRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Multi-format book reader repository implementation
 */
public class MultiFormatBookReaderRepository implements BookReaderRepository {
    private final Map<BookFormat, FormatReader> readers;
    private FormatReader currentReader;
    
    public MultiFormatBookReaderRepository() {
        readers = new HashMap<>();
        readers.put(BookFormat.PDF, new PdfReader());
        readers.put(BookFormat.TXT, new TxtReader());
        readers.put(BookFormat.EPUB, new EpubBookReader());
        // MOBI uses TXT reader as fallback
        readers.put(BookFormat.MOBI, new TxtReader());
    }
    
    @Override
    public boolean openBook(String filePath) {
        try {
            // Close any currently open book
            closeBook();
            
            // Determine format from file extension
            BookFormat format = BookFormat.fromFilename(filePath);
            currentReader = readers.get(format);
            
            if (currentReader == null) {
                throw new IllegalArgumentException("Unsupported format: " + format);
            }
            
            currentReader.open(filePath);
            return true;
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error opening book: " + e.getMessage());
            currentReader = null;
            return false;
        }
    }
    
    @Override
    public Optional<BookContent> getPage(int pageNumber) {
        if (currentReader == null || !currentReader.isOpen()) {
            return Optional.empty();
        }
        
        try {
            return Optional.of(currentReader.getPage(pageNumber));
        } catch (IOException | IndexOutOfBoundsException e) {
            System.err.println("Error reading page: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    @Override
    public int getTotalPages() {
        return currentReader != null ? currentReader.getTotalPages() : 0;
    }
    
    @Override
    public void closeBook() {
        if (currentReader != null) {
            currentReader.close();
            currentReader = null;
        }
    }
    
    @Override
    public boolean isBookOpen() {
        return currentReader != null && currentReader.isOpen();
    }
}
