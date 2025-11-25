package com.readcore.android.adapters.readers

import com.readcore.android.domain.entities.BookContent
import com.readcore.android.domain.entities.ContentType
import com.readcore.android.domain.repositories.BookReaderRepository
import org.jsoup.Jsoup
import java.io.File
import java.util.zip.ZipFile

class EpubBookReader : BookReaderRepository {
    private var pages: List<String> = emptyList()
    private var currentFilePath: String? = null

    override suspend fun openBook(filePath: String): Boolean {
        try {
            closeBook()

            val file = File(filePath)
            if (!file.exists()) {
                return false
            }

            val pagesList = mutableListOf<String>()
            ZipFile(file).use { zip ->
                zip.entries().asSequence().forEach { entry ->
                    if (entry.name.endsWith(".html") || entry.name.endsWith(".xhtml")) {
                        zip.getInputStream(entry).use { input ->
                            val content = input.bufferedReader().use { it.readText() }
                            val doc = Jsoup.parse(content)
                            val text = doc.body()?.text() ?: ""
                            if (text.isNotBlank()) {
                                pagesList.add(text)
                            }
                        }
                    }
                }
            }

            pages = pagesList
            currentFilePath = filePath
            return pages.isNotEmpty()
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
