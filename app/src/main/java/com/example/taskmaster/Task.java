package com.example.taskmaster;

public class Task {
    private String title;
    private boolean isCompleted;
    private int priority; // 0: Baja, 1: Media, 2: Alta

    public Task(String title, boolean isCompleted, int priority) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.priority = priority;
    }

    // Getters y Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}