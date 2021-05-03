package com.crewmeister.cmcodingchallenge.currency.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CurrencyExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String currency;
    private double exchangeValue;
    private double percentageExchange;
    private Date date;

    public CurrencyExchange() {
    }

    public CurrencyExchange(String currency, double exchangeValue, double percentageExchange, Date date) {
        this.currency = currency;
        this.exchangeValue = exchangeValue;
        this.percentageExchange = percentageExchange;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public double getExchangeValue() {
        return exchangeValue;
    }

    public Date getDate() {
        return date;
    }

    public double getPercentageExchange() {
        return percentageExchange;
    }

    public void setPercentageExchange(double percentageExchange) {
        this.percentageExchange = percentageExchange;
    }
}
