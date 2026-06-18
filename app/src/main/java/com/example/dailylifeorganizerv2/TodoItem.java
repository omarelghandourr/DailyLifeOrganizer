package com.example.dailylifeorganizerv2;

public class TodoItem {

    private int id;
    private String task;
    private boolean completed;

    public TodoItem(int id, String task, boolean completed) {
        this.id = id;
        this.task = task;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}