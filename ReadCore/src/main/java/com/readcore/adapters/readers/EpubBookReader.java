package com.readcore.adapters.readers;

import com.readcore.domain.entities.BookContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * EPUB format reader implementation (simplified)
 * EPUB files are essentially ZIP archives containing XHTML files
 */
public class EpubBookReader implements FormatReader {
    private List<String> pages;
    private boolean isOpen;
    
    @Override
    public void open(String filePath) throws IOException {
        pages = new ArrayList<>();
        isOpen = false;
        
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String entryName = entry.getName().toLowerCase();
                
                // Look for XHTML/HTML content files
                if (!entry.isDirectory() && 
                    (entryName.endsWith(".xhtml") || entryName.endsWith(".html") || entryName.endsWith(".htm"))) {
                    
                    // Read the content
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zipIn.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    
                    String content = new String(out.toByteArray(), "UTF-8");
                    pages.add(content);
                }
                zipIn.closeEntry();
            }
        }
        
        // If no pages found, add a placeholder
        if (pages.isEmpty()) {
            pages.add("<html><body><p>EPUB file format not fully supported. Content could not be extracted.</p></body></html>");
        }
        
        isOpen = true;
    }
    
    @Override
    public BookContent getPage(int pageNumber) throws IOException {
        if (!isOpen || pages == null) {
            throw new IllegalStateException("No document is open");
        }
        
        if (pageNumber < 0 || pageNumber >= pages.size()) {
            throw new IndexOutOfBoundsException("Invalid page number: " + pageNumber);
        }
        
        String content = pages.get(pageNumber);
        
        // Clean up the HTML content for better display
        try {
            Document doc = Jsoup.parse(content);
            content = doc.body().html();
        } catch (Exception e) {
            // If parsing fails, use original content
        }
        
        return new BookContent(content, pageNumber, BookContent.ContentType.HTML);
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
        isOpen = false;
    }
    
    @Override
    public boolean isOpen() {
        return isOpen;
    }
}
