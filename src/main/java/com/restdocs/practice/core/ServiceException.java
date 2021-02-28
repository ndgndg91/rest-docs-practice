package com.restdocs.practice.core;

public class ServiceException extends RuntimeException{
    private final int statusCode;

    public ServiceException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
