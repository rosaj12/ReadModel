package com.readcore.usecases;

import com.readcore.domain.repositories.BookmarkRepository;
import com.readcore.domain.repositories.BookRepository;
import com.readcore.domain.repositories.ReadingProgressRepository;

/**
 * Use case for removing a book from the library
 */
public class RemoveBookUseCase {
    private final BookRepository bookRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ReadingProgressRepository progressRepository;
    
    public RemoveBookUseCase(BookRepository bookRepository,
                           BookmarkRepository bookmarkRepository,
                           ReadingProgressRepository progressRepository) {
        this.bookRepository = bookRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.progressRepository = progressRepository;
    }
    
    public void execute(String bookId) {
        // Remove all related data
        bookmarkRepository.deleteByBookId(bookId);
        progressRepository.delete(bookId);
        bookRepository.delete(bookId);
    }
}
