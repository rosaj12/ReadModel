package com.readcore.android.framework.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.readcore.android.framework.AppContainer
import com.readcore.android.framework.ui.screens.LibraryScreen
import com.readcore.android.framework.ui.screens.ReaderScreen
import com.readcore.android.framework.viewmodels.LibraryViewModel
import com.readcore.android.framework.viewmodels.LibraryViewModelFactory
import com.readcore.android.framework.viewmodels.ReaderViewModel
import com.readcore.android.framework.viewmodels.ReaderViewModelFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

sealed class Screen(val route: String) {
    object Library : Screen("library")
    object Reader : Screen("reader/{bookId}") {
        fun createRoute(bookId: String) = "reader/$bookId"
    }
}

@Composable
fun ReadCoreApp(appContainer: AppContainer) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Library.route) {
        composable(Screen.Library.route) {
            val viewModel: LibraryViewModel = viewModel(
                factory = LibraryViewModelFactory(appContainer)
            )
            val uiState by viewModel.uiState.collectAsState()

            LibraryScreen(
                books = uiState.books,
                isLoading = uiState.isLoading,
                error = uiState.error,
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { viewModel.searchBooks(it) },
                onAddBook = { viewModel.addBook(it) },
                onRemoveBook = { viewModel.removeBook(it) },
                onOpenBook = { bookId ->
                    navController.navigate(Screen.Reader.createRoute(bookId))
                },
                onClearError = { viewModel.clearError() }
            )
        }

        composable(
            route = Screen.Reader.route,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
            val viewModel: ReaderViewModel = viewModel(
                factory = ReaderViewModelFactory(appContainer, bookId)
            )
            val uiState by viewModel.uiState.collectAsState()

            ReaderScreen(
                book = uiState.book,
                currentPage = uiState.currentPage,
                totalPages = uiState.totalPages,
                pageContent = uiState.pageContent,
                bookmarks = uiState.bookmarks,
                fontSize = uiState.fontSize,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onPreviousPage = { viewModel.previousPage() },
                onNextPage = { viewModel.nextPage() },
                onGoToPage = { viewModel.goToPage(it) },
                onCreateBookmark = { viewModel.createBookmark(it) },
                onIncreaseFontSize = { viewModel.increaseFontSize() },
                onDecreaseFontSize = { viewModel.decreaseFontSize() },
                onBack = { navController.popBackStack() },
                onClearError = { viewModel.clearError() }
            )
        }
    }
}
