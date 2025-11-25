package com.readcore.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Bookmark entity - represents a saved position in a book
 */
public class Bookmark {
    private final String id;
    private final String bookId;
    private final int pageNumber;
    private final String note;
    private final LocalDateTime createdDate;
    
    public Bookmark(String id, String bookId, int pageNumber, String note) {
        this.id = Objects.requireNonNull(id, "Bookmark ID cannot be null");
        this.bookId = Objects.requireNonNull(bookId, "Book ID cannot be null");
        this.pageNumber = pageNumber;
        this.note = note;
        this.createdDate = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public String getNote() {
        return note;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return id.equals(bookmark.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
