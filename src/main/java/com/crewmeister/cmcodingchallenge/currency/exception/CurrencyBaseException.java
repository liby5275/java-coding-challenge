package com.crewmeister.cmcodingchallenge.currency.exception;

public class CurrencyBaseException extends RuntimeException{

    private final int statusCode;

    public CurrencyBaseException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
