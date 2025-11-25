package com.readcore.android.adapters.readers

import com.readcore.android.domain.entities.BookContent
import com.readcore.android.domain.entities.ContentType
import com.readcore.android.domain.repositories.BookReaderRepository
import java.io.File

class TxtBookReader : BookReaderRepository {
    private var pages: List<String> = emptyList()
    private var currentFilePath: String? = null
    private val linesPerPage = 40

    override suspend fun openBook(filePath: String): Boolean {
        try {
            closeBook()

            val file = File(filePath)
            if (!file.exists()) {
                return false
            }

            val lines = file.readLines()
            val pagesList = mutableListOf<String>()

            for (i in lines.indices step linesPerPage) {
                val pageLines = lines.subList(i, minOf(i + linesPerPage, lines.size))
                pagesList.add(pageLines.joinToString("\n"))
            }

            pages = pagesList
            currentFilePath = filePath
            return true
        } catch (e: Exception) {
            closeBook()
            return false
        }
    }

    override suspend fun getPage(pageNumber: Int): BookContent? {
        if (pageNumber < 0 || pageNumber >= pages.size) {
            return null
        }

        return BookContent(
            content = pages[pageNumber],
            pageNumber = pageNumber,
            contentType = ContentType.TEXT
        )
    }

    override suspend fun getTotalPages(): Int = pages.size

    override suspend fun closeBook() {
        pages = emptyList()
        currentFilePath = null
    }

    override suspend fun isBookOpen(): Boolean = pages.isNotEmpty()
}
