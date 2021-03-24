package com.example.ultratracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Planner {

    private List<Task> todo;
    private static TaskDatabaseHelper db;
    public Planner (TaskDatabaseHelper db) {
        this.db = db;
        todo = new ArrayList<Task>();
    }


    public boolean addTask(Task newTask) {
        if(db.addOne(newTask)) {
            todo.add(newTask);
            return true;
        }
        return false;
    }

    public boolean removeTask(Task task) {
        if(db.deleteTask(task)) {
            todo.remove(task);
            return true;
        }
        return false;
    }

    // The method must be given the updated task in
    public boolean editTask(Task task) {
        for(Task toUpdate : todo) {
            if(toUpdate.getKey() == task.getKey()) {
                toUpdate.setName(task.getName());
                toUpdate.setAssignedDate(LocalDate.parse(task.getAssignedDate()));
                toUpdate.setDueDate(LocalDate.parse(task.getDueDate()));
                toUpdate.setDueTime(LocalTime.parse(task.getDueTime()));
                toUpdate.setDescription(task.getDescription());
                toUpdate.setPriority(task.getPriority());
                toUpdate.setComplete(task.isComplete());
                return db.updateAll(toUpdate);
            }
        }
        return false;
    }

    public boolean markAsComplete(Task task) {
        for(Task toUpdate : todo) {
            if(toUpdate.getKey() == task.getKey()) {
                toUpdate.setComplete(true);
                return db.updateAll(toUpdate);
            }
        }
        return false;
    }

    public List<Task> getAllTasks() {
        todo = db.getAll();
        return todo;
    }
}
