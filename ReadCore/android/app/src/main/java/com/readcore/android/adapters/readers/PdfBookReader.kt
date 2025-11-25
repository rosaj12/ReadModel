package com.readcore.android.adapters.readers

import com.readcore.android.domain.entities.BookContent
import com.readcore.android.domain.entities.ContentType
import com.readcore.android.domain.repositories.BookReaderRepository
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File

class PdfBookReader : BookReaderRepository {
    private var currentDocument: PDDocument? = null
    private var currentFilePath: String? = null
    private var totalPages: Int = 0

    override suspend fun openBook(filePath: String): Boolean {
        try {
            closeBook()

            val file = File(filePath)
            if (!file.exists()) {
                return false
            }

            currentDocument = PDDocument.load(file)
            currentFilePath = filePath
            totalPages = currentDocument?.numberOfPages ?: 0
            return true
        } catch (e: Exception) {
            closeBook()
            return false
        }
    }

    override suspend fun getPage(pageNumber: Int): BookContent? {
        val doc = currentDocument ?: return null

        if (pageNumber < 0 || pageNumber >= totalPages) {
            return null
        }

        return try {
            val stripper = PDFTextStripper()
            stripper.startPage = pageNumber + 1
            stripper.endPage = pageNumber + 1
            val text = stripper.getText(doc)

            BookContent(
                content = text,
                pageNumber = pageNumber,
                contentType = ContentType.TEXT
            )
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getTotalPages(): Int = totalPages

    override suspend fun closeBook() {
        currentDocument?.close()
        currentDocument = null
        currentFilePath = null
        totalPages = 0
    }

    override suspend fun isBookOpen(): Boolean = currentDocument != null
}
