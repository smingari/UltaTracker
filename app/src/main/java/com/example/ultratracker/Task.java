package com.example.ultratracker;

public class Task {
    String name;
    String dueDate;
    String dueTime;
    String description;

    int priority;
    boolean complete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
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

    public Task(String name, String dueDate, String dueTime, String description, int priority, boolean complete) {
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.description = description;
        this.priority = priority;
        this.complete = complete;
    }
}
