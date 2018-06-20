package com.n26.n26.controller;

public class ApiResponse {

    private int status;
    private String message;

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
