package com.readcore.usecases;

import com.readcore.domain.entities.ReadingProgress;
import com.readcore.domain.repositories.ReadingProgressRepository;

import java.util.Optional;

/**
 * Use case for getting reading progress
 */
public class GetReadingProgressUseCase {
    private final ReadingProgressRepository progressRepository;
    
    public GetReadingProgressUseCase(ReadingProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }
    
    public Optional<ReadingProgress> execute(String bookId) {
        return progressRepository.findByBookId(bookId);
    }
}
