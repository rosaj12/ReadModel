package com.readcore.adapters.readers;

import com.readcore.domain.entities.BookContent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TXT format reader implementation
 */
public class TxtReader implements FormatReader {
    private List<String> pages;
    private static final int LINES_PER_PAGE = 40;
    
    @Override
    public void open(String filePath) throws IOException {
        pages = new ArrayList<>();
        StringBuilder currentPage = new StringBuilder();
        int lineCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                currentPage.append(line).append("\n");
                lineCount++;
                
                if (lineCount >= LINES_PER_PAGE) {
                    pages.add(currentPage.toString());
                    currentPage = new StringBuilder();
                    lineCount = 0;
                }
            }
            
            // Add remaining content as last page
            if (currentPage.length() > 0) {
                pages.add(currentPage.toString());
            }
        }
        
        // Ensure at least one page
        if (pages.isEmpty()) {
            pages.add("");
        }
    }
    
    @Override
    public BookContent getPage(int pageNumber) throws IOException {
        if (pages == null) {
            throw new IllegalStateException("No document is open");
        }
        
        if (pageNumber < 0 || pageNumber >= pages.size()) {
            throw new IndexOutOfBoundsException("Invalid page number: " + pageNumber);
        }
        
        return new BookContent(pages.get(pageNumber), pageNumber, BookContent.ContentType.TEXT);
    }
    
    @Override
    public int getTotalPages() {
        return pages != null ? pages.size() : 0;
    }
    
    @Override
    public void close() {
        if (pages != null) {
            pages.clear();
            pages = null;
        }
    }
    
    @Override
    public boolean isOpen() {
        return pages != null;
    }
}
