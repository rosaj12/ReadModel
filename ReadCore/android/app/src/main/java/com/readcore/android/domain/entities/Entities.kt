package com.readcore.android.domain.entities

import java.time.LocalDateTime

/**
 * Book entity - represents a digital book
 */
data class Book(
    val id: String,
    val title: String,
    val author: String,
    val filePath: String,
    val format: BookFormat,
    val addedDate: LocalDateTime = LocalDateTime.now(),
    val coverImagePath: String? = null,
    val totalPages: Int = 0,
    val fileSize: Long = 0
)

enum class BookFormat(val extension: String) {
    PDF(".pdf"),
    EPUB(".epub"),
    TXT(".txt"),
    MOBI(".mobi");

    companion object {
        fun fromExtension(extension: String): BookFormat {
            return values().find { it.extension.equals(extension, ignoreCase = true) }
                ?: throw IllegalArgumentException("Formato não suportado: $extension")
        }

        fun fromFilename(filename: String): BookFormat {
            val lastDot = filename.lastIndexOf('.')
            if (lastDot == -1) {
                throw IllegalArgumentException("Nenhuma extensão encontrada: $filename")
            }
            return fromExtension(filename.substring(lastDot))
        }
    }
}

data class BookContent(
    val content: String,
    val pageNumber: Int,
    val contentType: ContentType
)

enum class ContentType {
    TEXT,
    HTML,
    IMAGE
}

data class ReadingProgress(
    val bookId: String,
    var currentPage: Int = 0,
    var totalPages: Int = 0,
    var percentageComplete: Double = 0.0,
    var lastReadDate: LocalDateTime = LocalDateTime.now()
) {
    fun updateProgress(currentPage: Int) {
        this.currentPage = currentPage
        this.percentageComplete = if (totalPages > 0) (currentPage * 100.0) / totalPages else 0.0
        this.lastReadDate = LocalDateTime.now()
    }
}

data class Bookmark(
    val id: String,
    val bookId: String,
    val pageNumber: Int,
    val note: String? = null,
    val createdDate: LocalDateTime = LocalDateTime.now()
)
