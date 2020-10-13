package com.honnalmanja.javamvvmpractice.model.remote.tasks;

import java.util.Date;

public class TasksResponse {

    private String taskID;
    private String taskDescription;
    private boolean taskCompleted;
    private Date createdAt;

    public TasksResponse(String taskID, String taskDescription, boolean taskCompleted, Date createdAt) {
        this.taskID = taskID;
        this.taskDescription = taskDescription;
        this.taskCompleted = taskCompleted;
        this.createdAt = createdAt;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "taskID='" + taskID + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskCompleted=" + taskCompleted +
                ", createdAt=" + createdAt +
                '}';
    }

}
