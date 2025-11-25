package com.readcore.usecases;

import com.readcore.domain.entities.BookContent;
import com.readcore.domain.repositories.BookReaderRepository;

import java.util.Optional;

/**
 * Use case for reading a page from a book
 */
public class ReadPageUseCase {
    private final BookReaderRepository bookReaderRepository;
    
    public ReadPageUseCase(BookReaderRepository bookReaderRepository) {
        this.bookReaderRepository = bookReaderRepository;
    }
    
    public Optional<BookContent> execute(int pageNumber) {
        if (!bookReaderRepository.isBookOpen()) {
            throw new IllegalStateException("No book is currently open");
        }
        
        if (pageNumber < 0 || pageNumber >= bookReaderRepository.getTotalPages()) {
            throw new IllegalArgumentException("Invalid page number: " + pageNumber);
        }
        
        return bookReaderRepository.getPage(pageNumber);
    }
    
    public int getTotalPages() {
        if (!bookReaderRepository.isBookOpen()) {
            return 0;
        }
        return bookReaderRepository.getTotalPages();
    }
}
