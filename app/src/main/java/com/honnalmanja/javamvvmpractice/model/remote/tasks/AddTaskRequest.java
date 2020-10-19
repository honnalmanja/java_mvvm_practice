package com.honnalmanja.javamvvmpractice.model.remote.tasks;

public class AddTaskRequest {

    private String description;
    private boolean isCompleted;

    public AddTaskRequest(String description, boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return "AddTaskRequest{" +
                "description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
