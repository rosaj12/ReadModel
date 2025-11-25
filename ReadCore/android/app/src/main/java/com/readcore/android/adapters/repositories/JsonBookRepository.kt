package com.readcore.android.adapters.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.readcore.android.domain.entities.Book
import com.readcore.android.domain.repositories.BookRepository
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "books")

class JsonBookRepository(private val context: Context) : BookRepository {
    private val gson = Gson()
    private val booksKey = stringPreferencesKey("books_data")

    override suspend fun save(book: Book) {
        val books = findAll().toMutableList()
        books.removeIf { it.id == book.id }
        books.add(book)
        saveBooks(books)
    }

    override suspend fun findById(id: String): Book? {
        return findAll().find { it.id == id }
    }

    override suspend fun findAll(): List<Book> {
        val preferences = context.dataStore.data.first()
        val json = preferences[booksKey] ?: return emptyList()
        val type = object : TypeToken<List<Book>>() {}.type
        return gson.fromJson(json, type)
    }

    override suspend fun searchByTitle(title: String): List<Book> {
        return findAll().filter { it.title.contains(title, ignoreCase = true) }
    }

    override suspend fun searchByAuthor(author: String): List<Book> {
        return findAll().filter { it.author.contains(author, ignoreCase = true) }
    }

    override suspend fun delete(id: String) {
        val books = findAll().filter { it.id != id }
        saveBooks(books)
    }

    override suspend fun exists(id: String): Boolean {
        return findById(id) != null
    }

    private suspend fun saveBooks(books: List<Book>) {
        context.dataStore.edit { preferences ->
            preferences[booksKey] = gson.toJson(books)
        }
    }
}
