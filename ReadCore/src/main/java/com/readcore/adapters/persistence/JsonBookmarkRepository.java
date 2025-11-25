package com.readcore.adapters.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.readcore.domain.entities.Bookmark;
import com.readcore.domain.repositories.BookmarkRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JSON-based implementation of BookmarkRepository
 */
public class JsonBookmarkRepository implements BookmarkRepository {
    private final String dataDirectory;
    private final Gson gson;
    private Map<String, Bookmark> bookmarks;
    
    public JsonBookmarkRepository(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        this.bookmarks = new HashMap<>();
        ensureDataDirectory();
        loadBookmarks();
    }
    
    private void ensureDataDirectory() {
        File dir = new File(dataDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    private File getBookmarksFile() {
        return new File(dataDirectory, "bookmarks.json");
    }
    
    private void loadBookmarks() {
        File file = getBookmarksFile();
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<Map<String, Bookmark>>(){}.getType();
                Map<String, Bookmark> loaded = gson.fromJson(reader, type);
                if (loaded != null) {
                    bookmarks = loaded;
                }
            } catch (IOException e) {
                System.err.println("Error loading bookmarks: " + e.getMessage());
            }
        }
    }
    
    private void saveBookmarks() {
        try (Writer writer = new FileWriter(getBookmarksFile())) {
            gson.toJson(bookmarks, writer);
        } catch (IOException e) {
            System.err.println("Error saving bookmarks: " + e.getMessage());
        }
    }
    
    @Override
    public void save(Bookmark bookmark) {
        bookmarks.put(bookmark.getId(), bookmark);
        saveBookmarks();
    }
    
    @Override
    public Optional<Bookmark> findById(String id) {
        return Optional.ofNullable(bookmarks.get(id));
    }
    
    @Override
    public List<Bookmark> findByBookId(String bookId) {
        return bookmarks.values().stream()
                .filter(bookmark -> bookmark.getBookId().equals(bookId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Bookmark> findAll() {
        return new ArrayList<>(bookmarks.values());
    }
    
    @Override
    public void delete(String id) {
        bookmarks.remove(id);
        saveBookmarks();
    }
    
    @Override
    public void deleteByBookId(String bookId) {
        bookmarks.values().removeIf(bookmark -> bookmark.getBookId().equals(bookId));
        saveBookmarks();
    }
}
