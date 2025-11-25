package com.readcore.domain.repositories;

import com.readcore.domain.entities.ReadingProgress;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ReadingProgress persistence
 */
public interface ReadingProgressRepository {
    /**
     * Save reading progress
     */
    void save(ReadingProgress progress);
    
    /**
     * Find reading progress for a book
     */
    Optional<ReadingProgress> findByBookId(String bookId);
    
    /**
     * Get all reading progress records
     */
    List<ReadingProgress> findAll();
    
    /**
     * Delete reading progress for a book
     */
    void delete(String bookId);
}
