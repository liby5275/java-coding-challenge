package com.crewmeister.cmcodingchallenge.currency.model.response;

public class CurrencyConversionResponse {

    private String toCurrency;
    private double amountInEuro;

    public CurrencyConversionResponse(String toCurrency, double amountInEuro) {
        this.toCurrency = toCurrency;
        this.amountInEuro = amountInEuro;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getAmountInEuro() {
        return amountInEuro;
    }

    public void setAmountInEuro(double amountInEuro) {
        this.amountInEuro = amountInEuro;
    }
}
