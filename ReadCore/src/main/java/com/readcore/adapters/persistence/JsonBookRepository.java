package com.readcore.adapters.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.readcore.domain.entities.Book;
import com.readcore.domain.repositories.BookRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JSON-based implementation of BookRepository for offline storage
 */
public class JsonBookRepository implements BookRepository {
    private final String dataDirectory;
    private final Gson gson;
    private Map<String, Book> books;
    
    public JsonBookRepository(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        this.books = new HashMap<>();
        ensureDataDirectory();
        loadBooks();
    }
    
    private void ensureDataDirectory() {
        File dir = new File(dataDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    private File getBooksFile() {
        return new File(dataDirectory, "books.json");
    }
    
    private void loadBooks() {
        File file = getBooksFile();
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<Map<String, Book>>(){}.getType();
                Map<String, Book> loaded = gson.fromJson(reader, type);
                if (loaded != null) {
                    books = loaded;
                }
            } catch (IOException e) {
                System.err.println("Error loading books: " + e.getMessage());
            }
        }
    }
    
    private void saveBooks() {
        try (Writer writer = new FileWriter(getBooksFile())) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }
    
    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
        saveBooks();
    }
    
    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }
    
    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
    
    @Override
    public List<Book> searchByTitle(String title) {
        String searchTerm = title.toLowerCase();
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Book> searchByAuthor(String author) {
        String searchTerm = author.toLowerCase();
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public void delete(String id) {
        books.remove(id);
        saveBooks();
    }
    
    @Override
    public boolean exists(String id) {
        return books.containsKey(id);
    }
}
