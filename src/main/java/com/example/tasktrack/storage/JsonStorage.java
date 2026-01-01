package com.example.tasktrack.storage;

import com.example.tasktrack.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage {
    private final Path file;
    private final Gson gson = new Gson();

    public JsonStorage(String fileName) {
        this.file = Paths.get(fileName);
        try {
            if (Files.notExists(file)) {
                Files.writeString(file, "[]"); 
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<Task> read() {
        try {
            String json = Files.readString(file);
            Type type = new TypeToken<List<Task>>(){}.getType();
            List<Task> list = gson.fromJson(json, type);
            return list == null ? new ArrayList<>() : list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void write(List<Task> tasks) {
        try {
            String json = gson.toJson(tasks);
            Files.writeString(file, json, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
