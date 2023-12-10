package com.example.springhexagonalarchitecturedemo.example.adapter.in.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;

    protected ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(0, "SUCCESS", null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, "SUCCESS", data);
    }
}
