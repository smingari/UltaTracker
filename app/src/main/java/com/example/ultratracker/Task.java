package com.example.ultratracker;

public class Task {

    private String taskText;
    private boolean completed;
    private static int keyCount = 0;  // used so the database can look up the task
    private int key;
    // Calendar class date
    // Private Calendar ______

    public Task(String taskText) {
        this.taskText = taskText;
        this.completed = false;
        this.key = keyCount;
        keyCount++;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
