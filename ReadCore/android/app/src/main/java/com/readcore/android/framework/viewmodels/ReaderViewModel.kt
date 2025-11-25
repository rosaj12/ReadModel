package com.readcore.android.framework.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.readcore.android.domain.entities.Book
import com.readcore.android.domain.entities.Bookmark
import com.readcore.android.domain.repositories.BookReaderRepository
import com.readcore.android.framework.AppContainer
import com.readcore.android.usecases.ReadPageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ReaderUiState(
    val book: Book? = null,
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val pageContent: String = "",
    val bookmarks: List<Bookmark> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val fontSize: Float = 16f
)

class ReaderViewModel(
    private val appContainer: AppContainer,
    private val bookId: String
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReaderUiState())
    val uiState: StateFlow<ReaderUiState> = _uiState.asStateFlow()

    private var bookReader: BookReaderRepository? = null
    private var readPageUseCase: ReadPageUseCase? = null

    init {
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val book = appContainer.openBookUseCase.execute(bookId)
                if (book == null) {
                    _uiState.value = _uiState.value.copy(
                        error = "Livro não encontrado",
                        isLoading = false
                    )
                    return@launch
                }

                bookReader = appContainer.getReaderForFormat(book.format)
                val opened = bookReader?.openBook(book.filePath) ?: false

                if (!opened) {
                    _uiState.value = _uiState.value.copy(
                        error = "Erro ao abrir o livro",
                        isLoading = false
                    )
                    return@launch
                }

                readPageUseCase = ReadPageUseCase(bookReader!!)
                val totalPages = readPageUseCase?.getTotalPages() ?: 0

                // Carregar progresso de leitura
                val progress = appContainer.getReadingProgressUseCase.execute(bookId)
                val startPage = progress?.currentPage ?: 0

                // Carregar marcadores
                val bookmarks = appContainer.getBookmarksUseCase.execute(bookId)

                _uiState.value = _uiState.value.copy(
                    book = book,
                    totalPages = totalPages,
                    bookmarks = bookmarks,
                    isLoading = false
                )

                goToPage(startPage)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Erro ao carregar livro: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun goToPage(pageNumber: Int) {
        viewModelScope.launch {
            try {
                val content = readPageUseCase?.execute(pageNumber)
                if (content != null) {
                    _uiState.value = _uiState.value.copy(
                        currentPage = pageNumber,
                        pageContent = content.content
                    )

                    // Atualizar progresso
                    appContainer.updateReadingProgressUseCase.execute(
                        bookId,
                        pageNumber,
                        _uiState.value.totalPages
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Erro ao carregar página: ${e.message}"
                )
            }
        }
    }

    fun nextPage() {
        val nextPage = _uiState.value.currentPage + 1
        if (nextPage < _uiState.value.totalPages) {
            goToPage(nextPage)
        }
    }

    fun previousPage() {
        val prevPage = _uiState.value.currentPage - 1
        if (prevPage >= 0) {
            goToPage(prevPage)
        }
    }

    fun createBookmark(note: String? = null) {
        viewModelScope.launch {
            try {
                val bookmark = appContainer.createBookmarkUseCase.execute(
                    bookId,
                    _uiState.value.currentPage,
                    note
                )
                val updatedBookmarks = _uiState.value.bookmarks + bookmark
                _uiState.value = _uiState.value.copy(bookmarks = updatedBookmarks)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Erro ao criar marcador: ${e.message}"
                )
            }
        }
    }

    fun increaseFontSize() {
        val newSize = (_uiState.value.fontSize + 2f).coerceAtMost(32f)
        _uiState.value = _uiState.value.copy(fontSize = newSize)
    }

    fun decreaseFontSize() {
        val newSize = (_uiState.value.fontSize - 2f).coerceAtLeast(10f)
        _uiState.value = _uiState.value.copy(fontSize = newSize)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            bookReader?.closeBook()
        }
    }
}

class ReaderViewModelFactory(
    private val appContainer: AppContainer,
    private val bookId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReaderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReaderViewModel(appContainer, bookId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
