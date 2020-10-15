package com.honnalmanja.javamvvmpractice.model.app;

import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;

import java.util.List;

public class TasksResponse {

    private int statusCode;
    private String message;
    private Task task;
    private List<Task> taskList;

    public TasksResponse(int statusCode, String message, Task task, List<Task> taskList) {
        this.statusCode = statusCode;
        this.message = message;
        this.task = task;
        this.taskList = taskList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Task getTask() {
        return task;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    @Override
    public String toString() {
        return "TasksResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", task=" + task +
                ", taskList=" + taskList +
                '}';
    }
}
