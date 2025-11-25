package com.readcore.usecases;

import com.readcore.domain.entities.Book;
import com.readcore.domain.repositories.BookRepository;

import java.util.List;

/**
 * Use case for searching books
 */
public class SearchBooksUseCase {
    private final BookRepository bookRepository;
    
    public SearchBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public List<Book> executeByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }
    
    public List<Book> executeByAuthor(String author) {
        return bookRepository.searchByAuthor(author);
    }
}
