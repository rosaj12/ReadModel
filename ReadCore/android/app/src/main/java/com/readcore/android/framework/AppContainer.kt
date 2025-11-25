package com.readcore.android.framework

import android.content.Context
import com.readcore.android.adapters.readers.EpubBookReader
import com.readcore.android.adapters.readers.PdfBookReader
import com.readcore.android.adapters.readers.TxtBookReader
import com.readcore.android.adapters.repositories.JsonBookRepository
import com.readcore.android.adapters.repositories.JsonBookmarkRepository
import com.readcore.android.adapters.repositories.JsonReadingProgressRepository
import com.readcore.android.domain.entities.BookFormat
import com.readcore.android.domain.repositories.BookReaderRepository
import com.readcore.android.domain.repositories.BookRepository
import com.readcore.android.domain.repositories.BookmarkRepository
import com.readcore.android.domain.repositories.ReadingProgressRepository
import com.readcore.android.usecases.*

/**
 * Dependency Injection Container
 */
class AppContainer(context: Context) {
    // Repositories
    val bookRepository: BookRepository = JsonBookRepository(context)
    val progressRepository: ReadingProgressRepository = JsonReadingProgressRepository(context)
    val bookmarkRepository: BookmarkRepository = JsonBookmarkRepository(context)

    // Readers
    private val pdfReader: BookReaderRepository = PdfBookReader()
    private val epubReader: BookReaderRepository = EpubBookReader()
    private val txtReader: BookReaderRepository = TxtBookReader()

    // Use Cases
    val addBookUseCase = AddBookUseCase(bookRepository)
    val getAllBooksUseCase = GetAllBooksUseCase(bookRepository)
    val openBookUseCase = OpenBookUseCase(bookRepository)
    val removeBookUseCase = RemoveBookUseCase(bookRepository, bookmarkRepository, progressRepository)
    val searchBooksUseCase = SearchBooksUseCase(bookRepository)
    val getReadingProgressUseCase = GetReadingProgressUseCase(progressRepository)
    val updateReadingProgressUseCase = UpdateReadingProgressUseCase(progressRepository)
    val createBookmarkUseCase = CreateBookmarkUseCase(bookmarkRepository)
    val getBookmarksUseCase = GetBookmarksUseCase(bookmarkRepository)

    fun getReaderForFormat(format: BookFormat): BookReaderRepository {
        return when (format) {
            BookFormat.PDF -> pdfReader
            BookFormat.EPUB -> epubReader
            BookFormat.TXT -> txtReader
            BookFormat.MOBI -> txtReader // MOBI é tratado como TXT
        }
    }
}
