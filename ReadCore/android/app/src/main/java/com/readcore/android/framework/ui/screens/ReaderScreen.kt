package com.readcore.android.framework.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.readcore.android.domain.entities.Book
import com.readcore.android.domain.entities.Bookmark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    book: Book?,
    currentPage: Int,
    totalPages: Int,
    pageContent: String,
    bookmarks: List<Bookmark>,
    fontSize: Float,
    isLoading: Boolean,
    error: String?,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit,
    onGoToPage: (Int) -> Unit,
    onCreateBookmark: (String?) -> Unit,
    onIncreaseFontSize: () -> Unit,
    onDecreaseFontSize: () -> Unit,
    onBack: () -> Unit,
    onClearError: () -> Unit
) {
    var showBookmarksDialog by remember { mutableStateOf(false) }
    var showGoToPageDialog by remember { mutableStateOf(false) }
    var showAddBookmarkDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = book?.title ?: "Leitor",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showBookmarksDialog = true }) {
                        Icon(Icons.Default.Bookmark, "Marcadores")
                    }
                    IconButton(onClick = { showGoToPageDialog = true }) {
                        Icon(Icons.Default.Pages, "Ir para página")
                    }
                }
            )
        },
        bottomBar = {
            ReaderBottomBar(
                currentPage = currentPage,
                totalPages = totalPages,
                onPreviousPage = onPreviousPage,
                onNextPage = onNextPage,
                onIncreaseFontSize = onIncreaseFontSize,
                onDecreaseFontSize = onDecreaseFontSize,
                onAddBookmark = { showAddBookmarkDialog = true }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                pageContent.isNotEmpty() -> {
                    PageContentView(
                        content = pageContent,
                        fontSize = fontSize
                    )
                }
            }

            error?.let {
                ErrorSnackbar(
                    message = it,
                    onDismiss = onClearError,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }

    if (showBookmarksDialog) {
        BookmarksDialog(
            bookmarks = bookmarks,
            onDismiss = { showBookmarksDialog = false },
            onGoToPage = { page ->
                onGoToPage(page)
                showBookmarksDialog = false
            }
        )
    }

    if (showGoToPageDialog) {
        GoToPageDialog(
            currentPage = currentPage,
            totalPages = totalPages,
            onDismiss = { showGoToPageDialog = false },
            onGoToPage = { page ->
                onGoToPage(page)
                showGoToPageDialog = false
            }
        )
    }

    if (showAddBookmarkDialog) {
        AddBookmarkDialog(
            currentPage = currentPage,
            onDismiss = { showAddBookmarkDialog = false },
            onConfirm = { note ->
                onCreateBookmark(note)
                showAddBookmarkDialog = false
            }
        )
    }
}

@Composable
fun PageContentView(
    content: String,
    fontSize: Float
) {
    val scrollState = rememberScrollState()

    Text(
        text = content,
        fontSize = fontSize.sp,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        lineHeight = (fontSize * 1.5).sp
    )
}

@Composable
fun ReaderBottomBar(
    currentPage: Int,
    totalPages: Int,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit,
    onIncreaseFontSize: () -> Unit,
    onDecreaseFontSize: () -> Unit,
    onAddBookmark: () -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                IconButton(onClick = onDecreaseFontSize) {
                    Icon(Icons.Default.TextDecrease, "Diminuir fonte")
                }
                IconButton(onClick = onIncreaseFontSize) {
                    Icon(Icons.Default.TextIncrease, "Aumentar fonte")
                }
                IconButton(onClick = onAddBookmark) {
                    Icon(Icons.Default.BookmarkAdd, "Adicionar marcador")
                }
            }

            Text(
                text = "Página ${currentPage + 1} de $totalPages",
                style = MaterialTheme.typography.bodyMedium
            )

            Row {
                IconButton(
                    onClick = onPreviousPage,
                    enabled = currentPage > 0
                ) {
                    Icon(Icons.Default.NavigateBefore, "Página anterior")
                }
                IconButton(
                    onClick = onNextPage,
                    enabled = currentPage < totalPages - 1
                ) {
                    Icon(Icons.Default.NavigateNext, "Próxima página")
                }
            }
        }
    }
}

@Composable
fun BookmarksDialog(
    bookmarks: List<Bookmark>,
    onDismiss: () -> Unit,
    onGoToPage: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Marcadores") },
        text = {
            if (bookmarks.isEmpty()) {
                Text("Nenhum marcador criado")
            } else {
                Column {
                    bookmarks.forEach { bookmark ->
                        TextButton(
                            onClick = { onGoToPage(bookmark.pageNumber) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Página ${bookmark.pageNumber + 1}",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                bookmark.note?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                        Divider()
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        }
    )
}

@Composable
fun GoToPageDialog(
    currentPage: Int,
    totalPages: Int,
    onDismiss: () -> Unit,
    onGoToPage: (Int) -> Unit
) {
    var pageInput by remember { mutableStateOf("${currentPage + 1}") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ir para Página") },
        text = {
            Column {
                Text("Digite o número da página (1-$totalPages):")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = pageInput,
                    onValueChange = { pageInput = it },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val page = pageInput.toIntOrNull()
                    if (page != null && page in 1..totalPages) {
                        onGoToPage(page - 1)
                    }
                }
            ) {
                Text("Ir")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun AddBookmarkDialog(
    currentPage: Int,
    onDismiss: () -> Unit,
    onConfirm: (String?) -> Unit
) {
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Marcador") },
        text = {
            Column {
                Text("Página ${currentPage + 1}")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text("Nota (opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(note.ifBlank { null })
                }
            ) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
