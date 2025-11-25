package com.readcore.usecases;

import com.readcore.domain.entities.Bookmark;
import com.readcore.domain.repositories.BookmarkRepository;

import java.util.UUID;

/**
 * Use case for creating a bookmark
 */
public class CreateBookmarkUseCase {
    private final BookmarkRepository bookmarkRepository;
    
    public CreateBookmarkUseCase(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }
    
    public Bookmark execute(String bookId, int pageNumber, String note) {
        String id = UUID.randomUUID().toString();
        Bookmark bookmark = new Bookmark(id, bookId, pageNumber, note);
        bookmarkRepository.save(bookmark);
        return bookmark;
    }
}
