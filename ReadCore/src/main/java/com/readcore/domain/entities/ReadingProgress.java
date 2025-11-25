package com.readcore.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ReadingProgress entity - tracks reader's progress in a book
 */
public class ReadingProgress {
    private final String bookId;
    private int currentPage;
    private int totalPages;
    private double percentageComplete;
    private LocalDateTime lastReadDate;
    
    public ReadingProgress(String bookId, int totalPages) {
        this.bookId = Objects.requireNonNull(bookId, "Book ID cannot be null");
        this.totalPages = totalPages;
        this.currentPage = 0;
        this.percentageComplete = 0.0;
        this.lastReadDate = LocalDateTime.now();
    }
    
    public void updateProgress(int currentPage) {
        this.currentPage = currentPage;
        this.percentageComplete = totalPages > 0 ? (currentPage * 100.0) / totalPages : 0.0;
        this.lastReadDate = LocalDateTime.now();
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        updateProgress(this.currentPage);
    }
    
    public double getPercentageComplete() {
        return percentageComplete;
    }
    
    public LocalDateTime getLastReadDate() {
        return lastReadDate;
    }
    
    public boolean isCompleted() {
        return currentPage >= totalPages - 1;
    }
}
