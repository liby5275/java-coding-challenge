package com.crewmeister.cmcodingchallenge.currency.exception;

public class CurrencyExchangeAppException {

    public static class RefreshBatchException extends CurrencyBaseException {

        public RefreshBatchException(String message, int statusCode) {
            super(message, statusCode);
        }
    }
}
