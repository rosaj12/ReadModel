package com.readcore.domain.repositories;

import com.readcore.domain.entities.BookContent;

import java.util.Optional;

/**
 * Repository interface for reading book content
 */
public interface BookReaderRepository {
    /**
     * Open a book for reading
     */
    boolean openBook(String filePath);
    
    /**
     * Get content for a specific page
     */
    Optional<BookContent> getPage(int pageNumber);
    
    /**
     * Get the total number of pages
     */
    int getTotalPages();
    
    /**
     * Close the currently open book
     */
    void closeBook();
    
    /**
     * Check if a book is currently open
     */
    boolean isBookOpen();
}
