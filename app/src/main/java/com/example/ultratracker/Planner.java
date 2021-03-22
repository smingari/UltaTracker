package com.example.ultratracker;

import java.util.ArrayList;

public class Planner {

    private ArrayList<Task> todo;

    public Planner () {
        todo = new ArrayList<Task>();
    }


    public void addTask(Task newTask) {
        todo.add(newTask);
    }

    public void removeTask(Task task) {
        if(todo.contains(task)) {
            todo.remove(task);
        }
    }

    public void editTask(Task task, String newText) {
        if(todo.contains(task)) {
            int index = todo.indexOf(task);
            todo.get(index).setDescription(newText);
        }
    }

    public void markAsComplete(Task task) {
        if(todo.contains(task)) {
            int index = todo.indexOf(task);
            todo.get(index).setComplete(true);
        }
    }

}
