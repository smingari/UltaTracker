package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    public static int Key = 0; // ID key for database

    private String name;
    private LocalDate dueDate;
    private LocalDateTime dueTime;
    private String description;
    private int key;

    private int priority;
    private boolean complete;

    public Task(String name, LocalDate dueDate, LocalDateTime dueTime, String description, int priority, boolean complete) {
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.description = description;
        this.priority = priority;
        this.complete = complete;
        this.key = Key++;
    }

    // default constructor
    // Times are all default for an hour in the same day
    public Task() {
        this.key = Key++;
        this.complete = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDateTime dueTime) {
        this.dueTime = dueTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getKey() {
        return this.key;
    }
}