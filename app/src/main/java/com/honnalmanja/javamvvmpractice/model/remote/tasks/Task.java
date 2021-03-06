package com.honnalmanja.javamvvmpractice.model.remote.tasks;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Task {

    @SerializedName("_id")
    private String taskID;

    @SerializedName("description")
    private String taskDescription;

    @SerializedName("completed")
    private boolean taskCompleted;

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("updatedAt")
    private Date updatedAt;

    public Task(String taskID, String taskDescription, boolean taskCompleted,
                Date createdAt, Date updatedAt) {
        this.taskID = taskID;
        this.taskDescription = taskDescription;
        this.taskCompleted = taskCompleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID='" + taskID + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskCompleted=" + taskCompleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
