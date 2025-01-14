package com.crewmeister.cmcodingchallenge.currency.exception;

public class CurrencyExchangeAppExceptions {

    private CurrencyExchangeAppExceptions() {
        throw new IllegalStateException("Utility Custom Exception class");
    }

    public static class RefreshBatchException extends CurrencyBaseException {

        public RefreshBatchException(String message, int statusCode) {
            super(message, statusCode);
        }
    }

    public static class FetchExchangeRatesException extends CurrencyBaseException {

        public FetchExchangeRatesException(String message, int statusCode) {
            super(message, statusCode);
        }

    }

    public static class InvalidInputException extends CurrencyBaseException {

        public InvalidInputException(String message, int statusCode) {
            super(message, statusCode);
        }

    }

}
