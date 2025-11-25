package com.readcore.usecases;

import com.readcore.domain.entities.Bookmark;
import com.readcore.domain.repositories.BookmarkRepository;

import java.util.List;

/**
 * Use case for getting all bookmarks for a book
 */
public class GetBookmarksUseCase {
    private final BookmarkRepository bookmarkRepository;
    
    public GetBookmarksUseCase(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }
    
    public List<Bookmark> execute(String bookId) {
        return bookmarkRepository.findByBookId(bookId);
    }
}
