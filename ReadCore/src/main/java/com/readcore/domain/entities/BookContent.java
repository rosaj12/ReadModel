package com.readcore.domain.entities;

/**
 * BookContent represents the content of a book page or section
 */
public class BookContent {
    private final String content;
    private final int pageNumber;
    private final ContentType contentType;
    
    public enum ContentType {
        TEXT,
        HTML,
        IMAGE
    }
    
    public BookContent(String content, int pageNumber, ContentType contentType) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.contentType = contentType;
    }
    
    public String getContent() {
        return content;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public ContentType getContentType() {
        return contentType;
    }
}
