package com.example.tasktrack.model;

import java.util.UUID;

public class Task {
    private String id;
    private String title;
    private String description;
    private String dueDate;
    private boolean completed;

    public Task() { } // needed for Gson

    public Task(String title, String description, String dueDate) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public boolean isCompleted() { return completed; }

    public void setComplete(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return title + " | " + dueDate + " | " + (completed ? "Done" : "Pending") + " | id:" + id;
    }
}
