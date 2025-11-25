package com.readcore.adapters.readers;

import com.readcore.domain.entities.BookContent;

import java.io.IOException;

/**
 * Interface for reading different book formats
 */
public interface FormatReader {
    /**
     * Open a book file
     */
    void open(String filePath) throws IOException;
    
    /**
     * Get content for a specific page
     */
    BookContent getPage(int pageNumber) throws IOException;
    
    /**
     * Get total number of pages
     */
    int getTotalPages();
    
    /**
     * Close the book
     */
    void close();
    
    /**
     * Check if book is open
     */
    boolean isOpen();
}
