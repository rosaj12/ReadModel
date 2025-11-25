package com.readcore.android.domain.repositories

import com.readcore.android.domain.entities.*

/**
 * Repository interfaces for Clean Architecture
 */
interface BookRepository {
    suspend fun save(book: Book)
    suspend fun findById(id: String): Book?
    suspend fun findAll(): List<Book>
    suspend fun searchByTitle(title: String): List<Book>
    suspend fun searchByAuthor(author: String): List<Book>
    suspend fun delete(id: String)
    suspend fun exists(id: String): Boolean
}

interface BookReaderRepository {
    suspend fun openBook(filePath: String): Boolean
    suspend fun getPage(pageNumber: Int): BookContent?
    suspend fun getTotalPages(): Int
    suspend fun closeBook()
    suspend fun isBookOpen(): Boolean
}

interface ReadingProgressRepository {
    suspend fun save(progress: ReadingProgress)
    suspend fun findByBookId(bookId: String): ReadingProgress?
    suspend fun findAll(): List<ReadingProgress>
    suspend fun delete(bookId: String)
}

interface BookmarkRepository {
    suspend fun save(bookmark: Bookmark)
    suspend fun findById(id: String): Bookmark?
    suspend fun findByBookId(bookId: String): List<Bookmark>
    suspend fun findAll(): List<Bookmark>
    suspend fun delete(id: String)
    suspend fun deleteByBookId(bookId: String)
}
