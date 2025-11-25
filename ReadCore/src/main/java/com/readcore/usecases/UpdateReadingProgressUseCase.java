package com.readcore.usecases;

import com.readcore.domain.entities.ReadingProgress;
import com.readcore.domain.repositories.ReadingProgressRepository;

/**
 * Use case for updating reading progress
 */
public class UpdateReadingProgressUseCase {
    private final ReadingProgressRepository progressRepository;
    
    public UpdateReadingProgressUseCase(ReadingProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }
    
    public void execute(String bookId, int currentPage, int totalPages) {
        ReadingProgress progress = progressRepository.findByBookId(bookId)
                .orElse(new ReadingProgress(bookId, totalPages));
        
        progress.setTotalPages(totalPages);
        progress.updateProgress(currentPage);
        
        progressRepository.save(progress);
    }
}
