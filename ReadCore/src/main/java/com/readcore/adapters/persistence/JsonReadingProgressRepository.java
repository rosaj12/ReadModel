package com.readcore.adapters.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.readcore.domain.entities.ReadingProgress;
import com.readcore.domain.repositories.ReadingProgressRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

/**
 * JSON-based implementation of ReadingProgressRepository
 */
public class JsonReadingProgressRepository implements ReadingProgressRepository {
    private final String dataDirectory;
    private final Gson gson;
    private Map<String, ReadingProgress> progressMap;
    
    public JsonReadingProgressRepository(String dataDirectory) {
        this.dataDirectory = dataDirectory;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        this.progressMap = new HashMap<>();
        ensureDataDirectory();
        loadProgress();
    }
    
    private void ensureDataDirectory() {
        File dir = new File(dataDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    private File getProgressFile() {
        return new File(dataDirectory, "reading_progress.json");
    }
    
    private void loadProgress() {
        File file = getProgressFile();
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<Map<String, ReadingProgress>>(){}.getType();
                Map<String, ReadingProgress> loaded = gson.fromJson(reader, type);
                if (loaded != null) {
                    progressMap = loaded;
                }
            } catch (IOException e) {
                System.err.println("Error loading progress: " + e.getMessage());
            }
        }
    }
    
    private void saveProgress() {
        try (Writer writer = new FileWriter(getProgressFile())) {
            gson.toJson(progressMap, writer);
        } catch (IOException e) {
            System.err.println("Error saving progress: " + e.getMessage());
        }
    }
    
    @Override
    public void save(ReadingProgress progress) {
        progressMap.put(progress.getBookId(), progress);
        saveProgress();
    }
    
    @Override
    public Optional<ReadingProgress> findByBookId(String bookId) {
        return Optional.ofNullable(progressMap.get(bookId));
    }
    
    @Override
    public List<ReadingProgress> findAll() {
        return new ArrayList<>(progressMap.values());
    }
    
    @Override
    public void delete(String bookId) {
        progressMap.remove(bookId);
        saveProgress();
    }
}
