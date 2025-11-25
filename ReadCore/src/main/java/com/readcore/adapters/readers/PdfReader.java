package com.readcore.adapters.readers;

import com.readcore.domain.entities.BookContent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * PDF format reader implementation
 */
public class PdfReader implements FormatReader {
    private PDDocument document;
    private String filePath;
    
    @Override
    public void open(String filePath) throws IOException {
        this.filePath = filePath;
        this.document = PDDocument.load(new File(filePath));
    }
    
    @Override
    public BookContent getPage(int pageNumber) throws IOException {
        if (document == null) {
            throw new IllegalStateException("No document is open");
        }
        
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(pageNumber + 1);
        stripper.setEndPage(pageNumber + 1);
        String text = stripper.getText(document);
        
        return new BookContent(text, pageNumber, BookContent.ContentType.TEXT);
    }
    
    @Override
    public int getTotalPages() {
        return document != null ? document.getNumberOfPages() : 0;
    }
    
    @Override
    public void close() {
        if (document != null) {
            try {
                document.close();
            } catch (IOException e) {
                System.err.println("Error closing PDF: " + e.getMessage());
            }
            document = null;
        }
    }
    
    @Override
    public boolean isOpen() {
        return document != null;
    }
}
