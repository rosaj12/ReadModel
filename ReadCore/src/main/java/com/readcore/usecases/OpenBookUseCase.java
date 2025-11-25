package com.readcore.usecases;

import com.readcore.domain.entities.Book;
import com.readcore.domain.repositories.BookRepository;

import java.util.Optional;

/**
 * Use case for opening a book for reading
 */
public class OpenBookUseCase {
    private final BookRepository bookRepository;
    
    public OpenBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public Optional<Book> execute(String bookId) {
        return bookRepository.findById(bookId);
    }
}
