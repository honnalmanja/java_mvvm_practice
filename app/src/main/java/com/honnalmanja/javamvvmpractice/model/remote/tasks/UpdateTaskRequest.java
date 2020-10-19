package com.honnalmanja.javamvvmpractice.model.remote.tasks;

import com.google.gson.annotations.SerializedName;

public class UpdateTaskRequest {

    @SerializedName("_id")
    private String taskID;

    @SerializedName("description")
    private String description;

    @SerializedName("completed")
    private boolean isCompleted = false;

    public UpdateTaskRequest(String description, boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getTaskID() {
        return taskID;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return "UpdateTaskRequest{" +
                "taskID='" + taskID + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
