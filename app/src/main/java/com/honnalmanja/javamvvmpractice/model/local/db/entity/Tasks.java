package com.honnalmanja.javamvvmpractice.model.local.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Tasks {

    @PrimaryKey
    @ColumnInfo(name = "task_id")
    private long taskID;

    @ColumnInfo(name = "task_remote_id")
    private String taskRemoteID;

    @ColumnInfo(name = "task_description")
    private String taskDescription;

    @ColumnInfo(name = "task_completed")
    private String taskCompleted;

    @ColumnInfo(name = "task_created_at")
    private String taskCreatedAt;

    @Ignore
    public Tasks(){}

    public Tasks(String taskDescription, String taskCompleted) {
        this.taskDescription = taskDescription;
        this.taskCompleted = taskCompleted;
    }

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
    }

    public String getTaskRemoteID() {
        return taskRemoteID;
    }

    public void setTaskRemoteID(String taskRemoteID) {
        this.taskRemoteID = taskRemoteID;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(String taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public String getTaskCreatedAt() {
        return taskCreatedAt;
    }

    public void setTaskCreatedAt(String taskCreatedAt) {
        this.taskCreatedAt = taskCreatedAt;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "taskID=" + taskID +
                ", taskRemoteID='" + taskRemoteID + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskCompleted='" + taskCompleted + '\'' +
                ", taskCreatedAt=" + taskCreatedAt +
                '}';
    }
}
