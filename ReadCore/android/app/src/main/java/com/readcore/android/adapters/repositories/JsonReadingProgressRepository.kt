package com.readcore.android.adapters.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.readcore.android.domain.entities.ReadingProgress
import com.readcore.android.domain.repositories.ReadingProgressRepository
import kotlinx.coroutines.flow.first

private val Context.progressDataStore by preferencesDataStore(name = "reading_progress")

class JsonReadingProgressRepository(private val context: Context) : ReadingProgressRepository {
    private val gson = Gson()
    private val progressKey = stringPreferencesKey("progress_data")

    override suspend fun save(progress: ReadingProgress) {
        val progressList = findAll().toMutableList()
        progressList.removeIf { it.bookId == progress.bookId }
        progressList.add(progress)
        saveProgress(progressList)
    }

    override suspend fun findByBookId(bookId: String): ReadingProgress? {
        return findAll().find { it.bookId == bookId }
    }

    override suspend fun findAll(): List<ReadingProgress> {
        val preferences = context.progressDataStore.data.first()
        val json = preferences[progressKey] ?: return emptyList()
        val type = object : TypeToken<List<ReadingProgress>>() {}.type
        return gson.fromJson(json, type)
    }

    override suspend fun delete(bookId: String) {
        val progressList = findAll().filter { it.bookId != bookId }
        saveProgress(progressList)
    }

    private suspend fun saveProgress(progressList: List<ReadingProgress>) {
        context.progressDataStore.edit { preferences ->
            preferences[progressKey] = gson.toJson(progressList)
        }
    }
}
