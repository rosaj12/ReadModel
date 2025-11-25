package com.readcore.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Book entity - represents a digital book in the system
 */
public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String filePath;
    private final BookFormat format;
    private final LocalDateTime addedDate;
    private String coverImagePath;
    private int totalPages;
    private long fileSize;
    
    public Book(String id, String title, String author, String filePath, BookFormat format) {
        this.id = Objects.requireNonNull(id, "Book ID cannot be null");
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.author = author != null ? author : "Unknown Author";
        this.filePath = Objects.requireNonNull(filePath, "File path cannot be null");
        this.format = Objects.requireNonNull(format, "Format cannot be null");
        this.addedDate = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public BookFormat getFormat() {
        return format;
    }
    
    public LocalDateTime getAddedDate() {
        return addedDate;
    }
    
    public String getCoverImagePath() {
        return coverImagePath;
    }
    
    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    public long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", format=" + format +
                '}';
    }
}
