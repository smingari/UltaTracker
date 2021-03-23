package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    public static int Key = 0; // ID key for database

    private String name;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private LocalTime dueTime;
    private String description;
    private int key;

    private int priority;
    private boolean complete;

    public Task(String name, LocalDate assignedDate, LocalDate dueDate, LocalTime dueTime, String description, int priority, boolean complete) {
        this.name = name;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.description = description;
        this.priority = priority;
        this.complete = complete;
        this.key = Key++;
    }

    // Constructor for db list since we don't want to increase the keys
    // DueDate and DueTime are string since that is how we receive them in the db
    // they are then converted to their api class
    public Task(String name, String assignedDate, String dueDate, String dueTime, String description, int priority, boolean complete, int key) {
        this.name = name;
        this.assignedDate = LocalDate.parse(assignedDate);
        this.dueDate = LocalDate.parse(dueDate);
        this.dueTime = LocalTime.parse(dueTime);
        this.description = description;
        this.priority = priority;
        this.complete = complete;
        this.key = key;
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

    public String getAssignedDate() {
        return assignedDate.toString();
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getDueDate() {
        return dueDate.toString();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // format is yyyy-mm-dd
    public String getDueTime() {
        return dueTime.toString();
    }

    public void setDueTime(LocalTime dueTime) {
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