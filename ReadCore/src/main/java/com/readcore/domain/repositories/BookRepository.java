package com.readcore.domain.repositories;

import com.readcore.domain.entities.Book;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book persistence
 */
public interface BookRepository {
    /**
     * Save a book to the library
     */
    void save(Book book);
    
    /**
     * Find a book by its ID
     */
    Optional<Book> findById(String id);
    
    /**
     * Get all books in the library
     */
    List<Book> findAll();
    
    /**
     * Search books by title
     */
    List<Book> searchByTitle(String title);
    
    /**
     * Search books by author
     */
    List<Book> searchByAuthor(String author);
    
    /**
     * Delete a book from the library
     */
    void delete(String id);
    
    /**
     * Check if a book exists
     */
    boolean exists(String id);
}
