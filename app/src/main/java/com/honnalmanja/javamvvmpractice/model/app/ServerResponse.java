package com.honnalmanja.javamvvmpractice.model.app;

public class ServerResponse {

    private boolean isSuccess;
    private String message;

    public ServerResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                '}';
    }
}
