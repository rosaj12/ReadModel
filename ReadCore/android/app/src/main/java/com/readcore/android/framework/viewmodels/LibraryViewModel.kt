package com.readcore.android.framework.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.readcore.android.domain.entities.Book
import com.readcore.android.framework.AppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LibraryUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

class LibraryViewModel(private val appContainer: AppContainer) : ViewModel() {
    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val books = appContainer.getAllBooksUseCase.execute()
                _uiState.value = _uiState.value.copy(books = books, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Erro ao carregar livros: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(searchQuery = query, isLoading = true)
            try {
                val books = if (query.isBlank()) {
                    appContainer.getAllBooksUseCase.execute()
                } else {
                    appContainer.searchBooksUseCase.executeByTitle(query)
                }
                _uiState.value = _uiState.value.copy(books = books, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Erro na busca: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun addBook(filePath: String) {
        viewModelScope.launch {
            try {
                appContainer.addBookUseCase.execute(filePath)
                loadBooks()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Erro ao adicionar livro: ${e.message}"
                )
            }
        }
    }

    fun removeBook(bookId: String) {
        viewModelScope.launch {
            try {
                appContainer.removeBookUseCase.execute(bookId)
                loadBooks()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Erro ao remover livro: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

class LibraryViewModelFactory(private val appContainer: AppContainer) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(appContainer) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
