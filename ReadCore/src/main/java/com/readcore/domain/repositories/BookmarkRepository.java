package com.readcore.domain.repositories;

import com.readcore.domain.entities.Bookmark;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Bookmark persistence
 */
public interface BookmarkRepository {
    /**
     * Save a bookmark
     */
    void save(Bookmark bookmark);
    
    /**
     * Find a bookmark by ID
     */
    Optional<Bookmark> findById(String id);
    
    /**
     * Find all bookmarks for a book
     */
    List<Bookmark> findByBookId(String bookId);
    
    /**
     * Get all bookmarks
     */
    List<Bookmark> findAll();
    
    /**
     * Delete a bookmark
     */
    void delete(String id);
    
    /**
     * Delete all bookmarks for a book
     */
    void deleteByBookId(String bookId);
}
