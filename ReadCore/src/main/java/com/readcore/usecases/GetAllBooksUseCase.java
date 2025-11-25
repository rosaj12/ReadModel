package com.readcore.usecases;

import com.readcore.domain.entities.Book;
import com.readcore.domain.repositories.BookRepository;

import java.util.List;

/**
 * Use case for getting all books in the library
 */
public class GetAllBooksUseCase {
    private final BookRepository bookRepository;
    
    public GetAllBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public List<Book> execute() {
        return bookRepository.findAll();
    }
}
