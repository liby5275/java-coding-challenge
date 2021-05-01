package com.crewmeister.cmcodingchallenge.currency.model.domain;

import java.util.Date;

public class CurrencyExchangeDTO {

    private String currency;
    private double value;
    private Date date;
    private double percentageChange;

    public CurrencyExchangeDTO() {
    }

    public CurrencyExchangeDTO(String currency, double value, Date date, double percentageChange) {
        this.currency = currency;
        this.value = value;
        this.date = date;
        this.percentageChange = percentageChange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    @Override
    public String toString() {
        return "CurrencyExchangeDTO{" +
                "currency='" + currency + '\'' +
                ", value=" + value +
                ", date=" + date +
                ", percentageChange=" + percentageChange +
                '}';
    }
}
