package com.crewmeister.cmcodingchallenge.currency.exception;

import org.springframework.http.HttpStatus;

public class CustomExceptionResponse {

    private HttpStatus status;
    private String message;
    private String url;

    public CustomExceptionResponse(HttpStatus status, String message, String url) {
        this.status = status;
        this.message = message;
        this.url = url;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }
}
