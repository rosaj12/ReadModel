package com.readcore.android.adapters.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.readcore.android.domain.entities.Bookmark
import com.readcore.android.domain.repositories.BookmarkRepository
import kotlinx.coroutines.flow.first

private val Context.bookmarkDataStore by preferencesDataStore(name = "bookmarks")

class JsonBookmarkRepository(private val context: Context) : BookmarkRepository {
    private val gson = Gson()
    private val bookmarksKey = stringPreferencesKey("bookmarks_data")

    override suspend fun save(bookmark: Bookmark) {
        val bookmarks = findAll().toMutableList()
        bookmarks.removeIf { it.id == bookmark.id }
        bookmarks.add(bookmark)
        saveBookmarks(bookmarks)
    }

    override suspend fun findById(id: String): Bookmark? {
        return findAll().find { it.id == id }
    }

    override suspend fun findByBookId(bookId: String): List<Bookmark> {
        return findAll().filter { it.bookId == bookId }
    }

    override suspend fun findAll(): List<Bookmark> {
        val preferences = context.bookmarkDataStore.data.first()
        val json = preferences[bookmarksKey] ?: return emptyList()
        val type = object : TypeToken<List<Bookmark>>() {}.type
        return gson.fromJson(json, type)
    }

    override suspend fun delete(id: String) {
        val bookmarks = findAll().filter { it.id != id }
        saveBookmarks(bookmarks)
    }

    override suspend fun deleteByBookId(bookId: String) {
        val bookmarks = findAll().filter { it.bookId != bookId }
        saveBookmarks(bookmarks)
    }

    private suspend fun saveBookmarks(bookmarks: List<Bookmark>) {
        context.bookmarkDataStore.edit { preferences ->
            preferences[bookmarksKey] = gson.toJson(bookmarks)
        }
    }
}
