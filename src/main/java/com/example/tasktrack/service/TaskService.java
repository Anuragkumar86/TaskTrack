package com.example.tasktrack.service;

import com.example.tasktrack.model.Task;
import com.example.tasktrack.storage.JsonStorage;

import java.util.List;
import java.util.Optional;



public class TaskService {
    private final JsonStorage storage;
    private List<Task> tasks;
    

    public TaskService(String file) {
        this.storage = new JsonStorage(file);
        this.tasks = storage.read();
    }

    public Task add(String title, String desc, String due) {
        Task t = new Task(title, desc, due);
        tasks.add(t);
        storage.write(tasks);
        return t;
    }

    public List<Task> list() {
        tasks = storage.read();
        return tasks;
    }

    public boolean complete(String id) {
        Optional<Task> opt = tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
        if (opt.isPresent()) {
            opt.get().setCompleted(true);
            storage.write(tasks);
            return true;
        }
        return false;
    }

    public boolean delete(String id) {
        boolean removed = tasks.removeIf(t -> t.getId().equals(id));
        if (removed) storage.write(tasks);
        return removed;
    }
}
