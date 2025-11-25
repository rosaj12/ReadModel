package com.readcore.android.usecases

import com.readcore.android.domain.entities.*
import com.readcore.android.domain.repositories.*
import java.io.File
import java.util.UUID

/**
 * Use Cases for ReadCore Android
 */
class AddBookUseCase(private val bookRepository: BookRepository) {
    suspend fun execute(filePath: String): Book {
        val file = File(filePath)
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("Arquivo não existe: $filePath")
        }

        val format = BookFormat.fromFilename(file.name)
        val title = extractTitle(file.name)
        val id = UUID.randomUUID().toString()

        val book = Book(
            id = id,
            title = title,
            author = "Autor Desconhecido",
            filePath = filePath,
            format = format,
            fileSize = file.length()
        )

        bookRepository.save(book)
        return book
    }

    private fun extractTitle(filename: String): String {
        val lastDot = filename.lastIndexOf('.')
        return if (lastDot > 0) filename.substring(0, lastDot) else filename
    }
}

class GetAllBooksUseCase(private val bookRepository: BookRepository) {
    suspend fun execute(): List<Book> = bookRepository.findAll()
}

class OpenBookUseCase(private val bookRepository: BookRepository) {
    suspend fun execute(bookId: String): Book? = bookRepository.findById(bookId)
}

class ReadPageUseCase(private val bookReaderRepository: BookReaderRepository) {
    suspend fun execute(pageNumber: Int): BookContent? {
        if (!bookReaderRepository.isBookOpen()) {
            throw IllegalStateException("Nenhum livro está aberto")
        }

        val totalPages = bookReaderRepository.getTotalPages()
        if (pageNumber < 0 || pageNumber >= totalPages) {
            throw IllegalArgumentException("Número de página inválido: $pageNumber")
        }

        return bookReaderRepository.getPage(pageNumber)
    }

    suspend fun getTotalPages(): Int = bookReaderRepository.getTotalPages()
}

class UpdateReadingProgressUseCase(private val progressRepository: ReadingProgressRepository) {
    suspend fun execute(bookId: String, currentPage: Int, totalPages: Int) {
        val progress = progressRepository.findByBookId(bookId)
            ?: ReadingProgress(bookId, totalPages = totalPages)

        progress.totalPages = totalPages
        progress.updateProgress(currentPage)

        progressRepository.save(progress)
    }
}

class GetReadingProgressUseCase(private val progressRepository: ReadingProgressRepository) {
    suspend fun execute(bookId: String): ReadingProgress? = progressRepository.findByBookId(bookId)
}

class CreateBookmarkUseCase(private val bookmarkRepository: BookmarkRepository) {
    suspend fun execute(bookId: String, pageNumber: Int, note: String?): Bookmark {
        val id = UUID.randomUUID().toString()
        val bookmark = Bookmark(id, bookId, pageNumber, note)
        bookmarkRepository.save(bookmark)
        return bookmark
    }
}

class GetBookmarksUseCase(private val bookmarkRepository: BookmarkRepository) {
    suspend fun execute(bookId: String): List<Bookmark> = bookmarkRepository.findByBookId(bookId)
}

class RemoveBookUseCase(
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val progressRepository: ReadingProgressRepository
) {
    suspend fun execute(bookId: String) {
        bookmarkRepository.deleteByBookId(bookId)
        progressRepository.delete(bookId)
        bookRepository.delete(bookId)
    }
}

class SearchBooksUseCase(private val bookRepository: BookRepository) {
    suspend fun executeByTitle(title: String): List<Book> = bookRepository.searchByTitle(title)
    suspend fun executeByAuthor(author: String): List<Book> = bookRepository.searchByAuthor(author)
}
